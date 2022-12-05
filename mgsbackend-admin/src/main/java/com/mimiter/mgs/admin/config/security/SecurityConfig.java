package com.mimiter.mgs.admin.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mimiter.mgs.admin.service.impl.UserDetailsServiceImpl;
import com.mimiter.mgs.common.model.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final AuthenticationErrorHandler authenticationErrorHandler;

    private final SessionAccessDeniedHandler accessDeniedHandler;

    private final ApplicationContext applicationContext;

    private final RedisIndexedSessionRepository sessionRepository;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityConfigUtil.exceptionHandling(http, authenticationErrorHandler, accessDeniedHandler);
        SecurityConfigUtil.configureDefault(http);
        SecurityConfigUtil.configureAnonymousAccess(http, applicationContext);

        // SpringSession
        http.sessionManagement()
                .maximumSessions(1)
                .sessionRegistry(sessionRegistry())
                .expiredSessionStrategy(new SessionExpiredStrategy());
    }

    static class SessionExpiredStrategy implements SessionInformationExpiredStrategy {

        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void onExpiredSessionDetected(SessionInformationExpiredEvent event)
                throws IOException, ServletException {
            HttpServletResponse response = event.getResponse();

            BaseResponse<Object> r = BaseResponse.error(HttpStatus.FORBIDDEN.value(), "会话已失效");

            String resp = objectMapper.writeValueAsString(r);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(resp);
            response.flushBuffer();
        }
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
