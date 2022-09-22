package com.mimiter.mgs.common.converter;

import com.mimiter.mgs.common.model.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class EnumConverterFactory implements ConverterFactory<String, BaseEnum>  {

    static final Map<Class, Converter> converterMap = new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> aClass) {
        return converterMap.computeIfAbsent(aClass, EnumConverter::new);
    }
}
