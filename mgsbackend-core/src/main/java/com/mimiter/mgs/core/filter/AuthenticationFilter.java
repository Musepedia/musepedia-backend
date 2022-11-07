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
