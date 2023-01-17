package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@ApiModel(value = "添加或更新展区请求参数")
@Data
public class UpsertExhibitionHallReq {

    @ApiModelProperty(value = "展区id", notes = "更新时必填")
    private Long id;

    @Length(max = 127, message = "展区名称长度不能超过127")
    @ApiModelProperty(value = "展区名称", notes = "添加展区时必填，长度不能超过127")
    private String name;

    @Length(max = 511, message = "展区描述长度不能超过511")
    @ApiModelProperty(value = "展区描述", notes = "添加展区时必填，长度不能超过511")
    private String description;

    @ApiModelProperty("展区图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "展区所在博物馆ID", notes = "添加展区时必填")
    private Long museumId;
}
