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
import org.springframework.security.authentication.BadCredentialsException;
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

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!"/login".equals(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!Methods.POST_STRING.equals(request.getMethod())) {
            handlerExceptionResolver.resolveException(request, response, null,
                    new AuthenticationServiceException(MessageConstant.METHOD_NOT_POST));
            //获取request方法
            return;
        }
        // 从 form 表单获取
        String username = request.getParameter("username");
        username = (username != null) ? username.trim() : null;
        String password = request.getParameter("password");
        // 从 body 获取
        if (!StringUtils.hasText(username) && !StringUtils.hasText(password)) {
            InputStreamReader isr = new InputStreamReader(request.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder sb = new StringBuilder();
            while((line = br.readLine()) != null) {
                sb.append(line);
            }
            LoginDTO loginDTO;
            try {
                loginDTO = JsonUtil.toobj(sb.toString(), LoginDTO.class);
            } catch (JsonProcessingException e) {
                handlerExceptionResolver.resolveException(request, response, null,
                        new ServiceException(MessageConstant.PARAM_ERROR));
                return;
            }
            username = loginDTO.getUsername();
            password = loginDTO.getPassword();
            if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
                handlerExceptionResolver.resolveException(request, response, null,
                        new ServiceException(MessageConstant.USERNAME_PASSWORD_NOT_BLANK));
                return;
            }
        }
        UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken
                .unauthenticated(username, password);
        try {
            Authentication authResult = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (BadCredentialsException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }
        AuthDTO token = authenticationService.authInfo();
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JsonUtil.toJsonStr(RestResponse.ok(token, MessageConstant.LOGIN_OK)));
    }
}
