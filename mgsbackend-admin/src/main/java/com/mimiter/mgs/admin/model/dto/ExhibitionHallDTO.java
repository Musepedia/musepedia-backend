package com.mimiter.mgs.admin.model.dto;

import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@ApiModel("展区信息")
@Data
public class ExhibitionHallDTO {

    private Long id;

    @ApiModelProperty("展区名")
    private String name;

    @ApiModelProperty("展区描述")
    private String description;

    @Deprecated
    @ApiModelProperty("展区图片URL(已过时)")
    private String imageUrl;

    @ApiModelProperty("展区图片URL列表，第一张为封面")
    private List<String> imageList = new ArrayList<>();

    @ApiModelProperty("展区所在博物馆信息")
    private Museum museum;

    @ApiModelProperty("展区是否启用")
    private Boolean enabled;
}
