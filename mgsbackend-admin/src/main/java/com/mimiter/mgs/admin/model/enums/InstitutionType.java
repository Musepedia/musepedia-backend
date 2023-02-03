package com.mimiter.mgs.admin.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mimiter.mgs.common.model.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * 机构类型枚举
 */
@AllArgsConstructor
public enum InstitutionType implements BaseEnum {

    MUSEUM(1, "博物馆负责人"),
    SCHOOL(2, "学校负责人");

    @EnumValue
    @JsonValue
    private final int value;

    private final String description;

    @Override
    public String getTextValue() {
        return value + "";
    }

    @Override
    public String getDescription() {
        return description;
    }
}
