package cn.abstractmgs.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@Configuration
@EnableRedisHttpSession
public class SpringSessionConfig {

    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver(){
        return new HeaderHttpSessionIdResolver("x-auth-token");
    }
}
