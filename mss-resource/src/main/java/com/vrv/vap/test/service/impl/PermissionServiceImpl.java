package com.vrv.vap.test.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.vrv.vap.test.service.PermissionService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujinhui
 * date 2021/3/21 20:59
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String requestUrl = request.getRequestURI();
        logger.info("客户端发送请求，requestUrl:{}", requestUrl);
        logger.info("客户端用户信息，principal:{}", principal);
        boolean hasPermission = false;
        List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();

        if (CollectionUtil.isEmpty(grantedAuthorityList)) {
            logger.warn("角色列表为空：{}", authentication.getPrincipal());
            return hasPermission;
        }

        String roleCodes = grantedAuthorityList.stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.joining(", "));

        logger.info("权限信息,{}", roleCodes);
        logger.info("{}", ReflectionToStringBuilder.toString(grantedAuthorityList, ToStringStyle.MULTI_LINE_STYLE));


        //  如果通过http://localhost:9030/oauth/token 获取token，直接进入到该方法，进行资源的判别是否有权限
        // principal 返回的org.springframework.security.authentication.AnonymousAuthenticationToken@6bc3b1a: Principal: anonymousUser; Credentials: [PROTECTED]; Authenticated: true; Details: org.springframework.security.web.authentication.WebAuthenticationDetails@b364:
        // 返回的权限是：Granted Authorities: ROLE_ANONYMOUS
        // 使用缓存，单机缓存Guava Cache
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if (CollectionUtils.isEmpty(grantedAuthorityList)) {
                return hasPermission;
            }
            for (SimpleGrantedAuthority authority : grantedAuthorityList) {
                // 需要判断url还要判断是get、patch、post等 ， requestMethod.equalsIgnoreCase(menu.getPathMethod())
                if (antPathMatcher.match(authority.getAuthority(), requestUrl) ) {  // 只能获取到uri，及request的请求方法（get、post，patch，delete等，一般都是role的角色的信息）
                    hasPermission = true;
                    break;
                }
            }
        } else {
            logger.error("匿名登录，没有访问权限，就算是ResourceServerConfigurerAdapter定义了权限！！！principal 返回的org.springframework.security.authentication.AnonymousAuthenticationToken");
        }
        if (!hasPermission) {
            logger.error("进行拦截，判断无权限访问，请求requestUrl:{}  权限：{}", requestUrl, roleCodes);
        }
        return hasPermission;
    }
}
