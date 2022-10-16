package com.mimiter.mgs.core.config;

import lombok.Data;
import org.ansj.library.DicLibrary;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class NLPConfig {

    public final String DICT_KEY = "custom";

    @Bean
    public void initCustomDictionary() {
        Forest forest = DicLibrary.get(DICT_KEY);
        if (forest == null) {
            DicLibrary.put(DICT_KEY, DICT_KEY, new Forest());
        }
    }
}
