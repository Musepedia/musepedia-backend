package com.mimiter.mgs.core.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mimiter.mgs.common.model.BaseEnum;
import com.mimiter.mgs.core.config.converter.CarouselTypeEcnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 轮播图类型枚举
 */
@Getter
@AllArgsConstructor
@JsonDeserialize(using = CarouselTypeEcnumConverter.class)
public enum CarouselType implements BaseEnum {

    LINK("link", "跳转外链"),
    DISPLAY("display", "小程序内展示");

    @EnumValue
    @JsonValue
    private final String type;

    private final String description;

    public static final String DICT_NAME = "carousel";

    @Override
    public String getTextValue() {
        return type;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
