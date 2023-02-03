package com.mimiter.mgs.admin.interceptor;

import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置当前线程用户ID
 */
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        try {
            Long id = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            SecurityUtil.setCurrentUserId(id);
        } catch (Exception ignore) {

        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        SecurityUtil.removeCurrentUserId();
    }
}
