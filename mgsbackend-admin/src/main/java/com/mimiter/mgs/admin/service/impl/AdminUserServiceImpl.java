package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.entity.UserRole;
import com.mimiter.mgs.admin.model.request.AddUserReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.model.request.ResetPasswordParam;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.repository.UserRoleRepository;
import com.mimiter.mgs.admin.service.AdminUserService;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

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

        // 校验密码
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 密码验证成功时会设置当前用户
        // see -> UserDetailsServiceImpl.loadUserByUsername
        return SecurityUtil.getCurrentUser();
    }

    @Override
    public void resetPassword(Long userId, ResetPasswordParam param) {
        AdminUser user = getNotNullById(userId);
        Assert.isTrue(encoder.matches(param.getOldPassword(), user.getPassword()), "原密码不正确");
        getBaseMapper().setPassword(userId, encoder.encode(param.getNewPassword()));
    }

}
