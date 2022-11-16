package com.mimiter.mgs.admin.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义用于SpringSecurity认证的token
 */
public class CodeAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public CodeAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
    }

    public CodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
