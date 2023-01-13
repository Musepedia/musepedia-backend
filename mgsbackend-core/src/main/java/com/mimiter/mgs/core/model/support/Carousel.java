package com.mimiter.mgs.core.model.support;

import com.mimiter.mgs.core.model.enums.CarouselType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页轮播图
 */
@Data
@ApiModel
public class Carousel {

    private String title;

    private String detail;

    private String img;

    @ApiModelProperty(value = "轮播图类型", allowableValues = "link, display")
    private CarouselType type;

    private String link;
}
