package com.vrv.vap.upms.service.impl;

import com.vrv.vap.upms.entity.SysRole;
import com.vrv.vap.upms.mapper.SysRoleMapper;
import com.vrv.vap.upms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 20:28
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Override
    public List<SysRole> getRoleByUserId(Integer userId) {
        return roleMapper.getRoleByUserId(userId);
    }
}
