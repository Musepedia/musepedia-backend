package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "管理员设置用户密码请求参数")
public class SetPasswordReq {

    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotBlank(message = "密码不能为空")
    private String password;
}
