package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

@ApiModel(value = "更新博物馆请求参数")
@Data
public class UpdateMuseumReq {


    private Long id;

    @ApiModelProperty(value = "博物馆名称")
    private String name;

    @ApiModelProperty(value = "博物馆描述")
    private String description;

    @ApiModelProperty(value = "博物馆logoURL")
    private String logoUrl;

    @ApiModelProperty(value = "博物馆图片URL")
    private String imageUrl;

    @ApiModelProperty(value = "博物馆地址")
    private String address;

    @ApiModelProperty(value = "博物馆经度")
    private Double longitude;

    @ApiModelProperty(value = "博物馆纬度")
    private Double latitude;
}
