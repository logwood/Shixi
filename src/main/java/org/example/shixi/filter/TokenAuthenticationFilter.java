package org.example.shixi.filter;

import org.example.shixi.constant.MessageConstant;
import org.example.shixi.properties.WhiteListProperties;
import org.example.shixi.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private WhiteListProperties whiteListProperties;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        if (isWhite(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        try {
            Authentication authResult = JWTUtil.decodeToken(token);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, new BadCredentialsException(MessageConstant.INVALID_TOKEN));
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isWhite(String uri) {
        return whiteListProperties.getWhiteList().stream().anyMatch(s -> pathMatcher.match(s, uri));
    }
}
