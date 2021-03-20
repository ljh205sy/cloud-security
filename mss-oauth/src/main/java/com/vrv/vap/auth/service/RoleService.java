package com.vrv.vap.auth.service;

import com.vrv.vap.auth.service.impl.RoleServiceFallbackFactory;
import com.vrv.vap.common.vo.Result;
import com.vrv.vap.common.vo.RoleVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 *
 * @author Mr.Yangxiufeng
 * @date 2017/12/29
 * Time:12:30
 * ProjectName:Mirco-Service-Skeleton
 */
@FeignClient(name = "mss-upms",fallback = RoleServiceFallbackFactory.class)
public interface RoleService {
    @GetMapping("role/getRoleByUserId/{userId}")
    Result<List<RoleVo>> getRoleByUserId(@PathVariable("userId") Integer userId);
}
