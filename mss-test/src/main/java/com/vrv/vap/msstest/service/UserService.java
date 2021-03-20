package com.vrv.vap.msstest.service;

import com.vrv.vap.common.vo.Result;
import com.vrv.vap.common.vo.UserVo;
import com.vrv.vap.msstest.service.impl.UserServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by Mr.Yangxiufeng on 2017/12/27.
 * Time:15:12
 * ProjectName:Mirco-Service-Skeleton
 */
@FeignClient(name = "mss-upms",fallback = UserServiceFallbackFactory.class)
public interface UserService {
    @GetMapping("user/findByUsername/{username}")
    Result<UserVo> findByUsername(@PathVariable("username") String username);
}
