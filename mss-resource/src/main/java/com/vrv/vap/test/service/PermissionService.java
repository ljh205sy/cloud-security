package com.vrv.vap.test.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujinhui
 * date 2021/3/21 20:58
 */
public interface PermissionService {

    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
