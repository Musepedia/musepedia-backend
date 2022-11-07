package com.mimiter.mgs.core.filter;

import com.mimiter.mgs.core.utils.ThreadContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用于获取每个请求携带的当前博物馆ID并存入ThreadLocal
 */
@Component
public class MuseumHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String museum = httpServletRequest.getHeader("x-museum");
        try {
            if (museum != null) {
                ThreadContextHolder.setCurrentMuseumId(Long.valueOf(museum));
            }
        } catch (NumberFormatException ignore) {

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
