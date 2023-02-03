package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "添加博物馆请求参数")
@Data
public class AddMuseumReq {

    @ApiModelProperty("博物馆名称")
    private String name;

    @ApiModelProperty("博物馆描述")
    private String description;

    @ApiModelProperty("博物馆logoURL")
    private String logoUrl;

    @ApiModelProperty("博物馆图片URL")
    private String imageUrl;

    @ApiModelProperty("博物馆地址")
    private String address;

    @ApiModelProperty("博物馆经度")
    private Double longitude;

    @ApiModelProperty("博物馆纬度")
    private Double latitude;

}
