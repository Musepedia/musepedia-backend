package com.mimiter.mgs.core.filter;

import com.mimiter.mgs.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>对于用户每个请求都会经过这个过滤器，将用户认证信息存入当前线程环境。</p>
 * <p>用户信息在处理完请求后会在{@link com.mimiter.mgs.core.interceptor.AuthenticationInterceptor}</p>中移除。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = httpServletRequest.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId != null) {
            SecurityUtil.setCurrentUserId(userId);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
