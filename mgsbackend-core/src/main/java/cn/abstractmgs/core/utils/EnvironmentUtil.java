package cn.abstractmgs.core.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil implements ApplicationContextAware {

    private static Environment env;

    private static boolean testEnv;

    public static boolean isTestEnv(){
        return testEnv;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        env = applicationContext.getEnvironment();
        testEnv = ArrayUtils.contains(env.getActiveProfiles(), "test");
    }
}
