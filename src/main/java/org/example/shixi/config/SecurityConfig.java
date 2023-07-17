package org.example.shixi.config;

import org.example.shixi.service.auth.AuthorizationDeniedHandler;
import org.example.shixi.filter.LoginAuthenticationFilter;
import org.example.shixi.filter.TokenAuthenticationFilter;
import org.example.shixi.properties.WhiteListProperties;
import org.example.shixi.service.auth.AuthorizationAccessDecisionVoter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   LoginAuthenticationFilter loginAuthenticationFilter,
                                                   TokenAuthenticationFilter tokenAuthenticationFilter,
                                                   AuthorizationAccessDecisionVoter authorizationAccessDecisionVoter,
                                                   AuthorizationDeniedHandler authorizationDeniedHandler,
                                                   WhiteListProperties whiteListProperties) throws Exception {
        List<AccessDecisionVoter<?>> accessDecisionVoterList = new ArrayList<>(1);
        accessDecisionVoterList.add(new WebExpressionVoter()); // authorizeRequests 配置的权限验证
        accessDecisionVoterList.add(authorizationAccessDecisionVoter);
        AccessDecisionManager accessDecisionManager = new UnanimousBased(accessDecisionVoterList);
        List<String> whiteList = whiteListProperties.getWhiteList();
        return httpSecurity
                .csrf().disable()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//因为前后端分离，所以要STATELESS
                .and()
                .authorizeRequests()
                .requestMatchers(whiteList.toArray(new String[0]))
                .permitAll().anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager)
                .and()
                .addFilterAfter(loginAuthenticationFilter, LogoutFilter.class)//增加filter
                .addFilterAfter(tokenAuthenticationFilter, LoginAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(authorizationDeniedHandler)//增加一个拒绝访问的handler
                .and()
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
