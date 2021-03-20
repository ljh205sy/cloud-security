package com.vrv.vap.auth.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: liujinhui
 * date: 2021/3/18 12:10
 */
@Component
public class VapMssOauthRequestFilter extends OncePerRequestFilter implements InitializingBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest reqeust, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = reqeust.getRequestURI();
        logger.info("认证服务器，请求uri:" + requestURI);
        filterChain.doFilter(reqeust, response);
    }
}
