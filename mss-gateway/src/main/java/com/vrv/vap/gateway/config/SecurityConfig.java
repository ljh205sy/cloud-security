package com.vrv.vap.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Mr.Yangxiufeng
 * @date 2017/12/29
 * Time:10:08
 * ProjectName:Mirco-Service-Skeleton
 */
@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    @Resource
    private ObjectMapper objectMapper;

    private static final String[] AUTH_WHITELIST = {
            "/**/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "swagger-resources/configuration/ui",
            "/doc.html",
            "/webjars/**"
    };

    /**
     * Spring Security 与 OAuth2（资源服务器）
     * 当资源服务器和认证服务器分离的时候，如果tokenStore使用的redis进行的存储，则需要注入redisTokenStore
     * https://www.jianshu.com/p/6dd03375224d
     */
    @Bean
    RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 包括token的生成与刷新操作
//        http.authorizeRequests().antMatchers("/v2/api-docs","/oauth/token","/mss-oauth/oauth/token").permitAll();
//        http.authorizeRequests().antMatchers("/v2/api-docs","/oauth/token").permitAll();
        http.authorizeRequests().antMatchers("/v2/api-docs", "/oauth/token", "/mss-oauth/oauth/token", "/oauth/authorize", "/oauth/check_token").permitAll();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        for (String au : AUTH_WHITELIST) {
            http.authorizeRequests().antMatchers(au).permitAll();
        }
        http.authorizeRequests().anyRequest().authenticated();
        registry.anyRequest().access("@permissionService.hasPermission(request,authentication)");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 原始一句话
//        resources.expressionHandler(expressionHandler);

        // 错误消息定制
        resources.tokenStore(redisTokenStore())
                .stateless(true)
                .authenticationEntryPoint(authenticationEntryPoint)   // 本类中@bean实现 AuthenticationEntryPoint
                .expressionHandler(expressionHandler)             // 本类中@bean实现 OAuth2WebSecurityExpressionHandler
                .accessDeniedHandler(oAuth2AccessDeniedHandler); // 本类中@bean实现 OAuth2AccessDeniedHandler
    }

    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }



    /**
     * 处理spring security oauth 处理失败返回消息格式
     */
    @Bean
    public OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                try (
                        Writer writer = response.getWriter()
                ) {
                    writer.write(objectMapper.writeValueAsString(authException.getMessage()));
                    writer.flush();
                }
            }
        };
    }

//    /**
//     * 未登录，返回401
//     *
//     * @return
//     */
//    @Bean
//    public AuthenticationEntryPoint authenticationEntryPoint() {
//        return (request, response, authException) -> ResponseUtil.responseFailed(objectMapper, response, authException.getMessage());
//    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthenticationEntryPoint(){
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
                    throws IOException, ServletException {
                logger.error("Responding with unauthorized error. Message:{}, url:{}", e.getMessage(), request.getRequestURI());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "无授权访问，Unauthorized");
            }
        };
    }


}
