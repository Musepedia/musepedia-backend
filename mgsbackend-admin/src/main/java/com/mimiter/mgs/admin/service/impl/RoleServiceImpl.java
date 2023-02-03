package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends AbstractCrudService<RoleRepository, Role> implements RoleService {

    @Override
    public List<Role> listUserRoles(Long userId) {
        return getBaseMapper().findByUserId(userId);
    }
}
