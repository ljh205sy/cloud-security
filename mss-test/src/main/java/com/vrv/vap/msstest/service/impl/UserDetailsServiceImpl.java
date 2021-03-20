package com.vrv.vap.msstest.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vrv.vap.common.util.StatusCode;
import com.vrv.vap.common.vo.MenuVo;
import com.vrv.vap.common.vo.Result;
import com.vrv.vap.common.vo.RoleVo;
import com.vrv.vap.common.vo.UserVo;
import com.vrv.vap.msstest.service.PermissionService;
import com.vrv.vap.msstest.service.RoleService;
import com.vrv.vap.msstest.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mr.Yangxiufeng
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UserVo> userResult = userService.findByUsername(username);
        if (userResult.getCode() != StatusCode.SUCCESS_CODE) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        boolean enabled = true; // 可用性 :true:可用 false:不可用
        boolean accountNonExpired = true; // 过期性 :true:没过期 false:过期
        boolean credentialsNonExpired = true; // 有效性 :true:凭证有效 false:凭证无效
        boolean accountNonLocked = true; // 锁定性 :true:未锁定 false:已锁定
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult.getData(),userVo);
        Result<List<RoleVo>> roleResult = roleService.getRoleByUserId(userVo.getId());
        
        /**
         * 如果不加这个url验证就是不启作用的， registry.anyRequest().access("@permissionService.hasPermission(request,authentication)");
         */
//        if (StatusCode.SUCCESS_CODE != roleResult.getCode()){
//            List<RoleVo> roleVoList = roleResult.getData();
//            for (RoleVo role:roleVoList){
//                //角色必须是ROLE_开头，可以在数据库中设置
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getValue());
//                grantedAuthorities.add(grantedAuthority);
//                //获取权限
//                Result<List<MenuVo>> perResult  = permissionService.getRolePermission(role.getId());
//                if (perResult.getCode() != StatusCode.SUCCESS_CODE){
//                    List<MenuVo> permissionList = perResult.getData();
//                    for (MenuVo menu:permissionList
//                            ) {
//                        GrantedAuthority authority = new SimpleGrantedAuthority(menu.getCode());
//                        grantedAuthorities.add(authority);
//                    }
//                }
//            }
//        }
        
        
        /**
         * 关于URL拦截测试
         */
        if (StatusCode.SUCCESS_CODE == roleResult.getCode()){
            List<RoleVo> roleVoList = roleResult.getData();
            for (RoleVo role:roleVoList){
                //角色必须是ROLE_开头，可以在数据库中设置
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+role.getValue());
                grantedAuthorities.add(grantedAuthority);
                //获取权限，根据role获取menu菜单的权限，把所有请求菜单的权限加入到authority中，在controller中可以使用 @PreAuthorize("hasAuthority('/consumer/authorized')") 进行权限的判断
                Result<List<MenuVo>> perResult  = permissionService.getRolePermission(role.getId());
                if (perResult.getCode() == StatusCode.SUCCESS_CODE){
                    List<MenuVo> permissionList = perResult.getData();
                    for (MenuVo menu:permissionList) {
                        GrantedAuthority authority = new SimpleGrantedAuthority(menu.getCode());
                        grantedAuthorities.add(authority);
                    }
                }
            }
        }
        User user = new User(userVo.getUsername(), userVo.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return user;
    }
}
