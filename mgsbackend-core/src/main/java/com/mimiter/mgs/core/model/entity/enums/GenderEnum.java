package com.mimiter.mgs.core.model.entity.enums;

import com.mimiter.mgs.common.model.BaseEnum;
import com.mimiter.mgs.core.config.converter.GenderEnumConverter;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户性别枚举
 */
@Getter
@AllArgsConstructor
@JsonDeserialize(using = GenderEnumConverter.class)
public enum GenderEnum implements BaseEnum {
    FEMALE(1, "女性"),
    MALE(2, "男性"),
    OTHER(3, "其他"),
    UNKNOWN(4, "未知");

    public static final String DICT_NAME = "gender";

    @EnumValue
    @JsonValue
    private final int gender;

    private final String description;

    @Override
    public String getTextValue() {
        return gender + "";
    }
}
