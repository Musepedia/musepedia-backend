package com.mimiter.mgs.common.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 判断当前开发环境工具类。
 */
@Component
public class EnvironmentUtil implements ApplicationContextAware {

    private static Environment env;

    private static boolean testEnv;

    /**
     * 判断当前是否是测试环境。<br>
     * 项目简化开发环境和测试环境统一为测试环境。
     *
     * @return /
     */
    public static boolean isTestEnv() {
        return testEnv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        env = applicationContext.getEnvironment();
        testEnv = ArrayUtils.contains(env.getActiveProfiles(), "test");
    }
}
