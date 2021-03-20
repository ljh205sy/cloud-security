package com.vrv.vap.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * <p>参看：https://github.com/spring-guides/tut-spring-security-and-angular-js/blob/master/oauth2-vanilla/README.adoc</p>
 ** 资源服务器和授权服务器不在同一个应用，则需告诉资源服务器令牌如何存储与解析，并与授权服务器使用相同的密钥进行解密
 * https://www.jianshu.com/p/6dd03375224d
 * @author wh1107066
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
}
