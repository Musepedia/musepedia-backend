package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员设置博物馆经营状态请求参数")
public class SetServicedReq {

    @NotNull(message = "博物馆ID不能为空")
    private Long id;

    @NotNull(message = "经营状态不能为空")
    private Boolean service;
}
