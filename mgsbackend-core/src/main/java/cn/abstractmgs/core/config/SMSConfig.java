package cn.abstractmgs.core.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mgs.sms")
public class SMSConfig {

    private String accessKeyId;

    private String accessKeySecret;

    @Bean
    public Client createClient(){
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret)
                .setEndpoint("dysmsapi.aliyuncs.com");
        try {
            return new Client(config);
        } catch (Exception e){
            return null;
        }
    }

}
