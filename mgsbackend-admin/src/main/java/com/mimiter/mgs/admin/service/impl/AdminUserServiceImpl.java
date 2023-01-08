package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.mapstruct.AdminUserDTOMapper;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.entity.UserRole;
import com.mimiter.mgs.admin.model.enums.InstitutionType;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.UpdateUserReq;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.repository.InstitutionAdminRepository;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRoleRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.admin.service.RoleService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 */
@Service("adminUserService")
@RequiredArgsConstructor
public class AdminUserServiceImpl
        extends AbstractCrudService<AdminUserRepository, AdminUser> implements AdminUserService {

    private final CaptchaService captchaService;

    private final RoleRepository roleRepository;

    private final UserRoleRepository userRoleRepository;

    private final InstitutionAdminRepository institutionAdminRepository;

    private final PasswordEncoder encoder;

    private final SessionRegistry sessionRegistry;

    private final AdminUserDTOMapper adminUserMapper;

    @Override
    @Transactional
    public AdminUser addUser(AddUserReq req) {
        Assert.isNull(getBaseMapper().findByUsername(req.getUsername()), "用户名已存在");

        AdminUser user = new AdminUser();
        user.setUsername(req.getUsername());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setNickname(req.getNickname());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        user.setEnabled(true);
        save(user);

        setRoles(user.getId(), req.getRoleIds(), req.getInstitutionId());

        return user;
    }

    private void setRoles(Long userId, List<Long> roleIds, Long institutionId) {
        Assert.notNull(userId, "用户ID不能为空");
        QueryWrapper<UserRole> ur = new QueryWrapper<>();
        ur.eq("user_id", userId);
        userRoleRepository.delete(ur);

        InstitutionAdmin ia = new InstitutionAdmin();
        ia.setUserId(userId);
        ia.setInstitutionId(institutionId);

        if (roleIds != null) {
            boolean isMuseumAdmin = false;
            boolean isSchoolAdmin = false;

            for (Long roleId : roleIds) {
                Role role = roleRepository.selectById(roleId);
                Assert.notNull(role, "角色ID: " + roleId + " 不存在");
                userRoleRepository.insert(new UserRole(userId, roleId));

                if (RoleService.STR_MUSEUM_ADMIN.equals(role.getName())) {
                    ia.setType(InstitutionType.MUSEUM);
                    isMuseumAdmin = true;
                } else if (RoleService.STR_SCHOOL_ADMIN.equals(role.getName())) {
                    ia.setType(InstitutionType.SCHOOL);
                    isSchoolAdmin = true;
                }
            }

            if (isMuseumAdmin && isSchoolAdmin) {
                throw new BadRequestException("不能同时设置为博物馆管理员和学校管理员");
            }

        }

        // 保存机构管理员信息
        if (ia.getInstitutionId() != null && ia.getType() != null) {
            if (institutionAdminRepository.selectById(userId) == null) {
                institutionAdminRepository.insert(ia);
            } else {
                institutionAdminRepository.updateById(ia);
            }
        }
    }

    @Override
    @Transactional
    public boolean updateById(UpdateUserReq req) {
        AdminUser user = new AdminUser();
        user.setId(req.getId());
        user.setUsername(req.getUsername());
        user.setNickname(req.getNickname());
        user.setPhone(req.getPhone());
        user.setEmail(req.getEmail());
        updateById(user);

        setRoles(user.getId(), req.getRoleIds(), req.getInstitutionId());

        return true;
    }

    @Override
    public AdminUser findByUsername(String username) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public AdminUser loginPassword(LoginReq req) {
        Assert.notNull(req, "登录请求不能为空");
        // 校验验证码
        if (!EnvironmentUtil.isTestEnv()) {
            captchaService.verifyCaptcha(req.getUuid(), req.getCode());
        }

        AdminUser user = findByUsername(req.getUsername());
        Assert.notNull(user, "用户名或密码错误");
        // 校验密码
        Assert.isTrue(encoder.matches(req.getPassword(), user.getPassword()), "用户名或密码错误");
        // 校验账号状态
        Assert.isTrue(user.getEnabled(), "账号已被禁用");

        List<Role> roles = roleRepository.findByUserId(user.getId());
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        CodeAuthenticationToken successToken = new CodeAuthenticationToken(authorities, user.getId());
        successToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(successToken);

        return user;
    }

    @Override
    public void setPassword(Long userId, String password) {
        AdminUser user = new AdminUser();
        user.setId(userId);
        user.setPassword(encoder.encode(password));
        updateById(user);
    }

    @Override
    public void setEnable(Long userId, boolean enable) {
        AdminUser user = new AdminUser();
        user.setId(userId);
        user.setEnabled(enable);
        updateById(user);

        if (!enable) {
            sessionRegistry.getAllSessions(userId, false)
                    .forEach(SessionInformation::expireNow);
        }
    }

    @EventListener
    public void onSessionDestroyed(SessionExpiredEvent event) {
        int i = 0;
    }

    @Override
    public boolean checkPassword(Long userId, String password) {
        AdminUser user = getById(userId);
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public UserDTO toDto(AdminUser user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = adminUserMapper.toDto(user);
        dto.setRoles(roleRepository.findByUserId(user.getId()));

        InstitutionAdmin ia = institutionAdminRepository.selectById(user.getId());
        dto.setInstitutionId(ia == null ? null : ia.getInstitutionId());
        return dto;
    }
}
