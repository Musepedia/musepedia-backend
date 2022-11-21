package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.repository.AdminUserRepository;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminUserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdminUser user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException("找不到用户名" + s);
        }

        String[] roles = roleRepository.findByUserId(user.getId())
                .stream().map(Role::getName).toArray(String[]::new);

        SecurityUtil.setCurrentUser(user);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(roles)
                .accountLocked(BooleanUtils.isNotTrue(user.getEnabled()))
                .build();
    }
}
