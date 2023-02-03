package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "设置启动状态请求参数")
public class SetEnableReq {

    @NotNull(message = "ID不能为空")
    private Long id;

    @NotNull(message = "启用状态不能为空")
    private Boolean enable;
}
