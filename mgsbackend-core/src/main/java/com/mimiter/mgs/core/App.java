package com.mimiter.mgs.core;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 核心后端模块。
 */
@RestController
@RequestMapping("/api")
@SpringBootApplication(scanBasePackages = {"com.mimiter.mgs"})
@ServletComponentScan
@EnableCaching
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @AnonymousAccess
    @GetMapping
    public String hello() {
        return "Hello MGS";
    }
}
