package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.config.security.CodeAuthenticationToken;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.entity.UserRole;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.UpdateUserReq;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRoleRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
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

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

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

        setRoles(user.getId(), req.getRoleIds());

        return user;
    }

    private void setRoles(Long userId, List<Long> roleIds) {
        Assert.notNull(userId, "用户ID不能为空");
        QueryWrapper<UserRole> ur = new QueryWrapper<>();
        ur.eq("user_id", userId);
        userRoleRepository.delete(ur);

        if (roleIds != null) {
            for (Long roleId : roleIds) {
                Role role = roleRepository.selectById(roleId);
                Assert.notNull(role, "角色ID: " + roleId + " 不存在");
                userRoleRepository.insert(new UserRole(userId, roleId));
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

        setRoles(user.getId(), req.getRoleIds());

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
        captchaService.verifyCaptcha(req.getUuid(), req.getCode());

        AdminUser user = findByUsername(req.getUsername());
        Assert.notNull(user, "用户名或密码错误");
        // 校验密码
        Assert.isTrue(encoder.matches(req.getPassword(), user.getPassword()), "用户名或密码错误");
        // 校验账号状态
        Assert.isTrue(user.getEnabled(), "账号已被禁用");

        List<Role> roles = roleRepository.findByUserId(user.getId());
        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

        CodeAuthenticationToken successToken = new CodeAuthenticationToken(authorities);
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
    }

    @Override
    public boolean checkPassword(Long userId, String password) {
        AdminUser user = getById(userId);
        return encoder.matches(password, user.getPassword());
    }

}
