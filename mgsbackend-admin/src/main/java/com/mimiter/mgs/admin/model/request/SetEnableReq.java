package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员设置用户启动状态请求参数")
public class SetEnableReq {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "启用状态不能为空")
    private Boolean enable;
}
