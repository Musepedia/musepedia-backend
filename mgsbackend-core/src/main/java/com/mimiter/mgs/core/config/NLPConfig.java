package com.mimiter.mgs.core.config;

import lombok.Data;
import org.ansj.library.DicLibrary;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class NLPConfig {

    public final String dictKey = "custom";

    @Bean
    public void initCustomDictionary() {
        Forest forest = DicLibrary.get(dictKey);
        if (forest == null) {
            DicLibrary.put(dictKey, dictKey, new Forest());
        }
    }
}
