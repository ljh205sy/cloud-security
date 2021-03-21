package com.vrv.vap.test.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrv.vap.test.utils.ResponseUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author wh1107066
 */
@Configuration
public class DefaultSecurityHandlerConfig {
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 未登录，返回401
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }

    /**
     * 处理spring security oauth 处理失败返回消息格式，自定义返回消息
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler() {

            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
               // 无权限访问，"Access is denied"
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
            }
        };
    }
}
