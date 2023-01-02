package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@ApiModel("添加/更新展品请求参数")
@Data
public class UpsertExhibitReq {

    @ApiModelProperty(value = "展品ID", notes = "更新时必填")
    private Long id;

    @ApiModelProperty(value = "展品对应展区ID", notes = "添加时必填")
    private Long hallId;

    @ApiModelProperty("展品图片URL")
    private String figureUrl;

    @Length(max = 127, message = "展品名称长度不能超过127")
    @ApiModelProperty(value = "展品名称", notes = "添加时必填，长度不能超过127")
    private String label;

    @Length(max = 511, message = "展品描述长度不能超过511")
    @ApiModelProperty(value = "展品描述", notes = "添加时必填，长度不能超过511")
    private String description;

    @ApiModelProperty("展品详细信息URL")
    private String url;

    @ApiModelProperty(value = "上一个展品ID")
    private Long prevId;

    @ApiModelProperty(value = "下一个展品ID")
    private Long nextId;
}
