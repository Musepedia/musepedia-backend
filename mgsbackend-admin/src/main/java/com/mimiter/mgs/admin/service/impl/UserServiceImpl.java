package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.mapstruct.UserMapper;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.model.param.LoginParam;
import com.mimiter.mgs.admin.model.param.ResetPasswordParam;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRepository;
import com.mimiter.mgs.admin.service.UserService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author AimStudy
 * @since 2022-04-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractCrudService<UserRepository, AdminUser> implements UserService {

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    public static final String DEFAULT_PASSWORD = "ab123456";

    @Override
    public AdminUser findByUsername(String username) {
        return getBaseMapper().findByUsername(username);
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    @Override
    public AdminUser loginPassword(LoginParam param) {
        Assert.notNull(param, "LoginParam must not be null");
        // 校验密码
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(param.getUsername(), param.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return SecurityUtil.getCurrentUser();
    }

    @Override
    public void forgetPassword(ResetPasswordParam param) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void resetPassword(Long userId, ResetPasswordParam param) {
        AdminUser user = getNotNullById(userId);
        Assert.isTrue(BCrypt.checkpw(param.getOldPassword(), user.getPassword()), "原密码不正确");
        getBaseMapper().setPassword(userId, BCrypt.hashpw(param.getNewPassword(), BCrypt.gensalt()));
    }

    @Override
    public UserDTO toDto(AdminUser user) {
        return userMapper.toDto(user);
    }

    @Override
    public List<UserDTO> toDto(List<AdminUser> users) {
        List<UserDTO> dtos = new ArrayList<>(users.size());
        for (AdminUser user : users) {
            dtos.add(toDto(user));
        }
        return dtos;
    }

    @Override
    public void resetPassword(Long userId) {
        Assert.notNull(userId, "User id must not be null");
        getBaseMapper().setPassword(userId, BCrypt.hashpw(DEFAULT_PASSWORD, BCrypt.gensalt()));
    }

    private List<Role> listUserRole(AdminUser user) {
        return roleRepository.findByUserId(user.getUserId());
    }
}
