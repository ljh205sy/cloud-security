package com.microservice.skeleton.auth.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Mr.Yangxiufeng
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @RequestMapping("/user")
    public Principal user(Principal user) {
        logger.info("{}", user);
        return user;
    }

    /**
     * 使用uri:  /demo 无权限访问， 因为authention中的授权未有该请求
     * authorities数组中没有anthority的属性没有 query-demo的授权
     */
    @RequestMapping("/demo")
   	@PreAuthorize("hasAuthority('query-demo')")
   	public String getDemo() {
   		return "good";
   	}

    /**
     * 有权限
     * authorities数组中没有anthority的属性没有 /consumer/authorized的授权
     * @return
     */
    @PreAuthorize("hasAuthority('/consumer/authorized')")
    @RequestMapping("/d00002")
    public Principal login(Principal user) {
        return user;
    }

    @RequestMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public Principal admin(Principal user) {
        return user;
    }

    /**
     * 通过zuul，需要加zuul负载的前缀，否则也是请求不成功的
     * 2021-03-18 11:08:28.813  INFO 22712 --- [nio-9030-exec-6] c.m.s.g.s.impl.PermissionServiceImpl     : 客户端发送请求，requestUrl:/mss-oauth/testzuul
     * 2021-03-18 11:08:28.813  INFO 22712 --- [nio-9030-exec-6] c.m.s.g.s.impl.PermissionServiceImpl     : 权限信息,/consumer/authorized, /consumer/hello, /consumer/user, /mss-oauth/testzuul, /provider/query, /provider/user, 0000, 00000001, ROLE_admin
     * @param user
     * @return
     */
    @RequestMapping("/testzuul")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public Principal testzuul(Principal user) {
        return user;
    }


    /**
     * 没有PreAuthorize的注解，所以不验证权限。
     * @return String
     */
    @RequestMapping("/d00003")
    public String test() {
        logger.info("进入到方法体..");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Authentication信息: {}", authentication);
        return "test";
    }
}
