package com.vrv.vap.msstest.service;


import com.vrv.vap.common.vo.MenuVo;
import com.vrv.vap.common.vo.Result;
import com.vrv.vap.msstest.service.impl.PermissionServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng on 2017/12/29.
 * Time:12:37
 * ProjectName:Mirco-Service-Skeleton
 */
@FeignClient(name = "mss-upms",fallback = PermissionServiceFallbackFactory.class)
public interface PermissionService {
    @GetMapping("permission/getRolePermission/{roleId}")
    Result<List<MenuVo>> getRolePermission(@PathVariable("roleId") Integer roleId);
}
