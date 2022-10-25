package com.mimiter.mgs.core.config;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mgs.sms")
public class SMSConfig {

    private String secretId;

    private String secretKey;

    private String sdkAppId;

    private String signName;

    private String templateId;

    @Bean
    public SmsClient createClient(){
        Credential cred = new Credential(secretId, secretKey);
        return new SmsClient(cred, "ap-nanjing");
    }

}
