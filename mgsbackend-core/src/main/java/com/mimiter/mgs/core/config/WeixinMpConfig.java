package com.mimiter.mgs.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mgs.weixin")
public class WeixinMpConfig {

    private String appid;

    private String secret;
}
