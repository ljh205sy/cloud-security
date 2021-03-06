package com.microservice.skeleton.auth.service.impl;

import com.microservice.skeleton.auth.service.UserService;
import com.microservice.skeleton.common.vo.Result;
import com.microservice.skeleton.common.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-06-13
 * Time: 10:56
 * @author wh1107066
 */
@Slf4j
@Component
public class UserServiceFallbackFactory implements UserService {
    @Override
    public Result<UserVo> findByUsername(String username) {
        log.info("调用{}失败","findByUsername");
        return Result.failure(100,"调用findByUsername接口失败");
    }
}
