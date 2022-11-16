package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.admin.model.entity.Role;
import com.mimiter.mgs.admin.repository.RoleRepository;
import com.mimiter.mgs.admin.repository.UserRepository;
import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AdminUser user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException(s + " not found");
        }

        SecurityUtil.setCurrentUser(user);
        String[] roles = roleRepository.findByUserId(user.getUserId())
                .stream().map(Role::getName).toArray(String[]::new);

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(roles)
                .build();
    }
}
