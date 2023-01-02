package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.service.base.CrudService;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleService extends CrudService<Role> {

    String STR_SYS_ADMIN = "sys_admin";

    String STR_MUSEUM_ADMIN = "museum_admin";

    String STR_SCHOOL_ADMIN = "school_admin";
    Role SYS_ADMIN = new Role(null, STR_SYS_ADMIN, "系统管理员");
    Role MUSEUM_ADMIN = new Role(null, STR_MUSEUM_ADMIN, "博物馆管理员");

    Role SCHOOL_ADMIN = new Role(null, STR_SCHOOL_ADMIN, "学校管理员");

    Role[] DEFAULT_ROLES = new Role[]{SYS_ADMIN, MUSEUM_ADMIN, SCHOOL_ADMIN};

    List<Role> listUserRoles(Long userId);
}
