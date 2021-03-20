package com.vrv.vap.gateway.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wh1107066
 */
public interface PermissionService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
