package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseEnum;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequestMapping("/api/edict")
@RestController
public class EnumDictController {

    private final Map<String, Map<String, String>> dict = new HashMap<>();

    public EnumDictController() {
        initDict();
    }

    @ApiOperation("获取后端枚举字典")
    @AnonymousAccess
    @GetMapping
    public BaseResponse<?> getDict(String name) {
        return BaseResponse.ok("ok", dict.get(name));
    }

    private void initDict() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(BaseEnum.class));
        Set<BeanDefinition> components = provider.findCandidateComponents("com.mimiter.mgs.core.model");
        for (BeanDefinition component : components) {
            try {
                Class<? extends BaseEnum> cls =
                        (Class<? extends BaseEnum>) Class.forName(component.getBeanClassName());
                Field field = ReflectionUtils.findField(cls, "DICT_NAME", String.class);
                if (field != null) {
                    BaseEnum[] enums = cls.getEnumConstants();
                    Map<String, String> dictContent = new HashMap<>();
                    for (BaseEnum e : enums) {
                        dictContent.put(e.getTextValue(), e.getDescription());
                    }

                    String dictName = (String) ReflectionUtils.getField(field, cls);
                    dict.put(dictName, dictContent);
                } else {
                    log.warn("枚举类{}找不到枚举字典名称，请添加public static String DICT_NAME字段", cls.getName());
                }
            } catch (ClassNotFoundException e) {
                log.error("Class not found: " + component.getBeanClassName(), e);
            }
        }
    }

}
