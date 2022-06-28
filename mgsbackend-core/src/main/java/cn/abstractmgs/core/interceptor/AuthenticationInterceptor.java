package cn.abstractmgs.core.interceptor;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.core.utils.SecurityUtil;
import cn.abstractmgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod)handler;
            if(method.hasMethodAnnotation(AnonymousAccess.class)){
                return true;
            }
            Long userId = SecurityUtil.getCurrentUserId();
            if(userId == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadContextHolder.removeAll();
    }
}
