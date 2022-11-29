package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.request.UpdateMuseumReq;
import com.mimiter.mgs.admin.repository.AdminMuseumRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.entity.UserRole;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.UpdateUserReq;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRoleRepository;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 博物馆服务实现类
 * </p>
 */
@Service("adminMuseumService")
@RequiredArgsConstructor
public class AdminMuseumServiceImpl
        extends AbstractCrudService<AdminMuseumRepository, Museum> implements AdminMuseumService {

    private final CaptchaService captchaService;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public Museum addMuseum(AddMuseumReq req) {
        Assert.isNull(getBaseMapper().findByName(req.getName()), "博物馆已存在");

        Museum museum = new Museum();
        museum.setName(req.getName());
        museum.setDescription(req.getDescription());
        museum.setLogoUrl(req.getLogUrl());
        museum.setImageUrl(req.getImageUrl());
        museum.setAddress(req.getAddress());
        museum.setService(true);
        museum.setFloorPlanFilepath(req.getFloorPlanFilepath());
        museum.setLongitude(req.getLongitude());
        museum.setLatitude(req.getLatitude());
        save(museum);

        return museum;
    }

    @Override
    @Transactional
    public boolean updateById(UpdateMuseumReq req) {
        Museum museum = new Museum();
        museum.setId(req.getId());
        museum.setName(req.getName());
        museum.setDescription(req.getDescription());
        museum.setLogoUrl(req.getLogUrl());
        museum.setImageUrl(req.getImageUrl());
        museum.setLongitude(req.getLongitude());
        museum.setLatitude(req.getLatitude());
        museum.setFloorPlanFilepath(req.getFloorPlanFilepath());
        museum.setAddress(req.getAddress());
        updateById(museum);

        return true;
    }

    @Override
    public void setServiced(Long id, boolean service) {
        Museum museum = new Museum();
        museum.setId(id);
        museum.setService(service);
        updateById(museum);
    }


}
