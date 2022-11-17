package com.mimiter.mgs.admin.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理类
 */
@Component
public class AuthenticationErrorHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        if (e instanceof BadCredentialsException) {
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "用户名或密码错误");
        } else if (e instanceof InsufficientAuthenticationException) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "权限不足");
        } else {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    e == null ? "Unauthorized" : e.getMessage());
        }
    }
}
