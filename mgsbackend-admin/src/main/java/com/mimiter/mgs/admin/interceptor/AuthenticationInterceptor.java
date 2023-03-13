package com.mimiter.mgs.admin.interceptor;

import com.mimiter.mgs.admin.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

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
            Object o = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (o instanceof Map) {
                var principal = (Map) o;
                Long userId = (Long) principal.get("userId");
                Long institutionId = (Long) principal.get("institutionId");
                SecurityUtil.setCurrentUserId(userId);
            } else if (o instanceof Long) {
                SecurityUtil.setCurrentUserId((Long) o);
            }
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
