package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.service.base.CrudService;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleService extends CrudService<Role> {

    Role SYS_ADMIN = new Role(null, "sys_admin", "系统管理员");
    Role MUSEUM_ADMIN = new Role(null, "museum_admin", "博物馆管理员");

    Role[] DEFAULT_ROLES = new Role[]{SYS_ADMIN, MUSEUM_ADMIN};

    List<Role> listUserRoles(Long userId);
}
