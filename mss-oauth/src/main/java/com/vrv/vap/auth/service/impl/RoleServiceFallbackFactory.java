package com.vrv.vap.auth.service.impl;

import com.vrv.vap.auth.service.RoleService;
import com.vrv.vap.common.vo.Result;
import com.vrv.vap.common.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-06-13
 * Time: 11:06
 * @author wh1107066
 */
@Slf4j
@Service
public class RoleServiceFallbackFactory implements RoleService {
    @Override
    public Result<List<RoleVo>> getRoleByUserId(Integer userId) {
        log.info("调用{}失败","getRoleByUserId");
        return Result.failure(100,"调用getRoleByUserId失败");
    }
}
