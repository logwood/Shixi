package org.example.shixi.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.undertow.util.Methods;
import org.example.shixi.constant.MessageConstant;
import org.example.shixi.controller.RestResponse;
import org.example.shixi.exception.ServiceException;
import org.example.shixi.service.auth.AuthenticationService;
import org.example.shixi.tables.dto.AuthDTO;
import org.example.shixi.tables.dto.LoginDTO;
import org.example.shixi.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 认证过滤器
 */
@Component
public class LoginAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService authenticationService;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * 重写doFilterInternal 方法
     * doFilter是一种特殊的方法，其用来进行doFilterInternal的函数，所以重写doFilterInternal就可以自定义Filter
     * 状态
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(!"login".equals(request.getServletPath())){
            filterChain.doFilter(request,response);
            return;
        }//若login放行。
        if(!Methods.POST_STRING.equals(request.getMethod())){
            handlerExceptionResolver.resolveException(request,response,null,
                    new AuthenticationServiceException(MessageConstant.METHOD_NOT_POST));
        }
        String username = request.getParameter("username");
        username = (username != null) ? username.trim() : null;
        //删除空白
        String password = request.getParameter("password");
        if(!StringUtils.hasText(username) && !StringUtils.hasText(password)){
            InputStreamReader inputStreamReader =new InputStreamReader(request.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder stringBuilder =new StringBuilder();
            while ((line = bufferedReader.readLine())!= null){
                stringBuilder.append(line);
            }
            LoginDTO loginDTO;
            try{
                loginDTO = JsonUtil.toobj(stringBuilder.toString(), LoginDTO.class);
            } catch (JsonProcessingException e){
                handlerExceptionResolver.resolveException(request,response,null,new ServiceException(MessageConstant.PARAM_ERROR));
                return;
            }
            //StringBuilder作为直接处理字符串的类，可以
            username = loginDTO.getUsername();
            password = loginDTO.getPassword();
            if(!StringUtils.hasText(username)|| !StringUtils.hasText(password)){
                handlerExceptionResolver.resolveException(request, response, null,
                        new ServiceException(MessageConstant.USERNAME_PASSWORD_NOT_BLANK));
                return;
            }
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=UsernamePasswordAuthenticationToken
                .unauthenticated(username,password);
        try {
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        AuthDTO authDTO =authenticationService.authInfo();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter()
                .write(JsonUtil.toJsonStr(RestResponse.ok(authDTO,MessageConstant.LOGIN_OK)));
    }
}
