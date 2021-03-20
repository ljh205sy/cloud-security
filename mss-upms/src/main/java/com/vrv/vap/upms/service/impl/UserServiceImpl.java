package com.vrv.vap.upms.service.impl;

import com.vrv.vap.upms.entity.SysUser;
import com.vrv.vap.upms.mapper.SysUserMapper;
import com.vrv.vap.upms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 19:57
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public SysUser findByUsername(String username) {
        Example example = Example.builder(SysUser.class)
                .where(Sqls.custom().andEqualTo("username",username))
                .build();
        return userMapper.selectOneByExample(example);
    }
}
