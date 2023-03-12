package com.mimiter.mgs.admin.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("文创dto")
public class CulturalCreativeDTO {

    @ApiModelProperty(value = "文创id", notes = "更新时必填")
    private Long id;

    @NotBlank(message = "文创产品名不能为空")
    @ApiModelProperty(value = "文创产品名", notes = "不能为空")
    private String name;

    @ApiModelProperty("文创描述")
    private String description;

    @ApiModelProperty("文创图片列表")
    private List<String> imageList = new ArrayList<>();

    @ApiModelProperty("文创对应博物馆id")
    private Long museumId;

    @ApiModelProperty("是否已下架")
    private Boolean deleted;
}
