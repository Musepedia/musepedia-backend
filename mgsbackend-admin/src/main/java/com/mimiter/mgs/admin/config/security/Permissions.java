package com.mimiter.mgs.admin.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

/**
 * 用于Controller层注解的权限校验
 */
@Service(value = "pm")
public class Permissions {

    /**
     * 校验当前用户是否拥有指定权限，超级管理员(sys_admin)默认拥有所有权限（返回true）
     *
     * @param permissions 权限列表
     * @return 是否拥有指定权限
     */
    public Boolean check(String... permissions) {
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return elPermissions.contains(STR_SYS_ADMIN) || contains(permissions);
    }

    /**
     * 校验当前用户是否拥有指定权限，与{@link #check(String...)}不同于不会默认通过超级管理员(sys_admin)权限
     *
     * @param permissions 权限列表
     * @return 是否拥有指定权限
     */
    public Boolean contains(String... permissions) {
        // 获取当前用户的所有权限
        List<String> elPermissions = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
