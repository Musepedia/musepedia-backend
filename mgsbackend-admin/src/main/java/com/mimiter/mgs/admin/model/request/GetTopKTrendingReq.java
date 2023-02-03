package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("获取热门物品请求")
public class GetTopKTrendingReq {

    @NotNull(message = "博物馆ID不能为空")
    @ApiModelProperty(value = "博物馆ID", notes = "超级管理员任意填写(非空)，其他管理员必须填写自己所属博物馆ID")
    private Long museumId;

    @SuppressWarnings("checkstyle:MemberName")
    @NotNull(message = "k不能为空")
    @Min(value = 1, message = "k必须大于0")
    @ApiModelProperty("前k热门物品")
    private Integer k;

}
