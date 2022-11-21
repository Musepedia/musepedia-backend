package com.mimiter.mgs.admin.service.check;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 启动时检查系统中是否存在指定角色和用户，没有则创建
 */
@Component
@RequiredArgsConstructor
public class InitCheckService {

    private final AdminUserService userService;

    private final RoleRepository roleRepository;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ensureRoleExists();
        ensureAdminExists();
    }

    private void ensureRoleExists() {
        for (Role role : RoleService.DEFAULT_ROLES) {
            QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", role.getName());
            if (roleRepository.selectCount(queryWrapper) == 0) {
                roleRepository.insert(role);
            }
        }
    }

    private void ensureAdminExists() {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", "mgs_admin");
        if (userService.count(queryWrapper) == 0) {
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.eq("name", RoleService.SYS_ADMIN.getName());
            Role sysAdmin = roleRepository.selectOne(roleQueryWrapper);

            AddUserReq req = new AddUserReq();
            req.setUsername("mgs_admin");
            req.setPassword("MGS_ADMIN_" + System.currentTimeMillis());
            req.setNickname("MGS管理员");
            req.setRoleIds(Collections.singletonList(sysAdmin.getId()));
            AdminUser admin = userService.addUser(req);
        }
    }
}