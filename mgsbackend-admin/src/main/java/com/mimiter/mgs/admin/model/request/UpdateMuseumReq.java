package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@ApiModel(value = "更新博物馆请求参数")
@Data
public class UpdateMuseumReq {

    private Long id;

    private String name;

    private String description;

    private String logUrl;

    private String imageUrl;

    private String address;

    //private Boolean service;

    private String floorPlanFilepath;

    private Double longitude;

    private Double latitude;
}
