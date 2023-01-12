package com.mimiter.mgs.admin.model.dto;

import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("展区信息")
@Data
public class ExhibitionHallDTO {

    private Long id;

    @ApiModelProperty("展区名")
    private String name;

    @ApiModelProperty("展区描述")
    private String description;

    @ApiModelProperty("展区图片URL")
    private String imageUrl;

    @ApiModelProperty("展区所在博物馆信息")
    private Museum museum;

    @ApiModelProperty("展区是否启用")
    private Boolean enabled;
}
