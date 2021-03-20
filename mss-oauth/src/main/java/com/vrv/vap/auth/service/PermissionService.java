package com.vrv.vap.auth.service;


import com.vrv.vap.auth.service.impl.PermissionServiceFallbackFactory;
import com.vrv.vap.common.vo.MenuVo;
import com.vrv.vap.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * @author wh1107066
 */
@FeignClient(name = "mss-upms",fallback = PermissionServiceFallbackFactory.class)
public interface PermissionService {
    @GetMapping("permission/getRolePermission/{roleId}")
    Result<List<MenuVo>> getRolePermission(@PathVariable("roleId") Integer roleId);
}
