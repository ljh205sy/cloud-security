package com.microservice.skeleton.auth.service.impl;

import com.microservice.skeleton.auth.service.PermissionService;
import com.microservice.skeleton.common.vo.MenuVo;
import com.microservice.skeleton.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wh1107066
 */
@Slf4j
@Service
public class PermissionServiceFallbackFactory implements PermissionService {
    @Override
    public Result<List<MenuVo>> getRolePermission(Integer roleId) {
        log.info("调用{}失败","getRolePermission");
        return Result.failure(100,"调用getRolePermission失败");
    }
}
