package com.mimiter.mgs.admin.config.security;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * SpringSecurity配置工具类
 */
public class SecurityConfigUtil {

    /**
     * 配置异常处理
     */
    public static HttpSecurity exceptionHandling(HttpSecurity http,
                                                 AuthenticationErrorHandler authenticationErrorHandler,
                                                 SessionAccessDeniedHandler accessDeniedHandler) throws Exception {
        return http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and();
    }

    /**
     * 默认配置，包括静态资源，websocket，swagger文档，options请求，actuator等
     */
    public static HttpSecurity configureDefault(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                // 静态资源等等
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/webSocket/**"
                ).permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 文件
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // spring actuator
                .antMatchers("/actuator/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .and();
    }

    /**
     * 自动扫描包含{@link AnonymousAccess}的Controller层方法来配置允许匿名访问的接口，
     * 同时也会对除了匿名可以访问的url配置鉴权
     */
    public static HttpSecurity configureAnonymousAccess(HttpSecurity http,
                                                        ApplicationContext context) throws Exception {
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap =
                context.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
        return http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType())
                        .toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType())
                        .toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType())
                        .toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType())
                        .toArray(new String[0])).permitAll()
                .antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType())
                        .toArray(new String[0])).permitAll()
                .antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType())
                        .toArray(new String[0])).permitAll()
                .anyRequest()
                .authenticated()
                .and();
    }

    private static Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
        Map<String, Set<String>> anonymousUrls = new HashMap<>(RequestMethodEnum.values().length);
        Set<String> get = new HashSet<>();
        Set<String> post = new HashSet<>();
        Set<String> put = new HashSet<>();
        Set<String> patch = new HashSet<>();
        Set<String> delete = new HashSet<>();
        Set<String> all = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            if (null != anonymousAccess) {
                List<RequestMethod> requestMethods =
                        new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
                RequestMethodEnum request = RequestMethodEnum.find(
                        requestMethods.size() == 0
                                ? RequestMethodEnum.ALL.getType()
                                : requestMethods.get(0).name());
                switch (Objects.requireNonNull(request)) {
                case GET:
                    get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                case POST:
                    post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                case PUT:
                    put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                case PATCH:
                    patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                case DELETE:
                    delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                default:
                    all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
                    break;
                }
            }
        }
        anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
        anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
        anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
        anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
        anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
        anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
        return anonymousUrls;
    }
}
