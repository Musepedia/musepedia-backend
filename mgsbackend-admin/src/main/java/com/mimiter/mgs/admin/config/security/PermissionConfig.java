package com.mimiter.mgs.admin.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用于Controller层注解的权限校验
 */
@Service(value = "pm")
public class PermissionConfig {

    /**
     * 校验当前用户是否拥有指定权限
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
        return elPermissions.contains("sys_admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
