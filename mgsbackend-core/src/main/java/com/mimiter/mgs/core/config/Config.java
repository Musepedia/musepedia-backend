package com.mimiter.mgs.core.config;

import com.mimiter.mgs.core.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.io.File;

/**
 * 后端整体配置，包括拦截器和静态资源配置。
 */
@Configuration
public class Config implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File path = new File("/root/mgs/figs");
        String realPath = "file:" + path.getAbsolutePath() + File.separator;
        registry.addResourceHandler("/mgs/figs" + "/**").addResourceLocations(realPath);
    }
}
