package com.mimiter.mgs.common.converter;

import com.mimiter.mgs.common.model.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 枚举转换器工厂。
 */
public class EnumConverterFactory implements ConverterFactory<String, BaseEnum>  {

    static final Map<Class, Converter> CONVERTER_MAP = new HashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> clazz) {
        return CONVERTER_MAP.computeIfAbsent(clazz, EnumConverter::new);
    }
}
