package com.vrv.vap.msstest.service.impl;

import com.vrv.vap.common.vo.MenuVo;
import com.vrv.vap.common.vo.Result;
import com.vrv.vap.msstest.service.PermissionService;
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
