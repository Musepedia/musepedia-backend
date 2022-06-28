package cn.abstractmgs.core.filter;

import cn.abstractmgs.core.utils.ThreadContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MuseumHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String museum = httpServletRequest.getHeader("x-museum");
        try {
            if(museum != null){
                ThreadContextHolder.setCurrentMuseumId(Long.valueOf(museum));
            }
        } catch (NumberFormatException ignore) {

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
