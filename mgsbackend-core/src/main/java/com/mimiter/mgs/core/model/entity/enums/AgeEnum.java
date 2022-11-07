package com.mimiter.mgs.core.model.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mimiter.mgs.common.model.BaseEnum;
import com.mimiter.mgs.core.config.converter.AgeEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户年龄枚举类。保存用户年龄时是保存分类，不保存具体年龄。
 */
@Getter
@AllArgsConstructor
@JsonDeserialize(using = AgeEnumConverter.class)
public enum AgeEnum implements BaseEnum {

    PRESCHOOL(1, "学龄前"),
    PRIMARY_SCHOOL_LOWER(2, "小学低年级"),
    PRIMARY_SCHOOL_HIGHER(3, "小学高年级"),
    JUNIOR_HIGH_SCHOOL(4, "初中"),
    HIGH_SCHOOL(5, "高中"),
    ADULT(6, "成人");

    public static final String DICT_NAME = "age";

    @EnumValue
    @JsonValue
    private final int age;

    private final String description;

    @Override
    public String getTextValue() {
        return age + "";
    }
}
