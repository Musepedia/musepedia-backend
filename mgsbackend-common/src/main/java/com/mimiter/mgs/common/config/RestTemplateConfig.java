package com.mimiter.mgs.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new ExtendedMappingJackson2HttpMessageConverter());
        return restTemplate;
    }

    static class ExtendedMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        public ExtendedMappingJackson2HttpMessageConverter(){
            setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        }
    }
}
