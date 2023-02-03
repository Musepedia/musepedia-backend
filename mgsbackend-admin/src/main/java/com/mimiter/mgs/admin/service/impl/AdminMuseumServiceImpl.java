package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.request.UpdateMuseumReq;
import com.mimiter.mgs.admin.repository.AdminMuseumRepository;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRoleRepository;
import com.mimiter.mgs.admin.service.AdminMuseumService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
        museum.setLogoUrl(req.getLogoUrl());
        museum.setImageUrl(req.getImageUrl());
        museum.setAddress(req.getAddress());
        museum.setService(true);
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
        museum.setLogoUrl(req.getLogoUrl());
        museum.setImageUrl(req.getImageUrl());
        museum.setLongitude(req.getLongitude());
        museum.setLatitude(req.getLatitude());
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
