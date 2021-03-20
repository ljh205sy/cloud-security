package com.vrv.vap.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.vrv.vap.gateway.constant.SecurityConstants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.FORM_BODY_WRAPPER_FILTER_ORDER;

/**
 * 将认证用户的相关信息放入header中, 后端服务可以直接读取使用
 */
@Component
public class UserInfoHeaderFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FORM_BODY_WRAPPER_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        logger.info("传递参数到后台!!!");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            RequestContext ctx = RequestContext.getCurrentContext();
            //客户端模式只返回一个clientId
            if (principal instanceof org.springframework.security.core.userdetails.User) {
                org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                ctx.addZuulRequestHeader(SecurityConstants.USER_HEADER, user.getUsername());
            } else if (principal instanceof String) {
                String username = (String) authentication.getPrincipal();
                logger.info("传递参数到后台...{}", username);
                ctx.addZuulRequestHeader(SecurityConstants.USER_HEADER, username);
            }
            ctx.addZuulRequestHeader(SecurityConstants.ROLE_HEADER, CollectionUtil.join(authentication.getAuthorities(), ","));
        }
        return null;
    }
}
