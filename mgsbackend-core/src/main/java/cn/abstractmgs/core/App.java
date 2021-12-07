package cn.abstractmgs.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 核心后端模块
 */
@SpringBootApplication(scanBasePackages = {"cn.abstractmgs"})
//@MapperScan("cn.abstractmgs.core.repository")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
