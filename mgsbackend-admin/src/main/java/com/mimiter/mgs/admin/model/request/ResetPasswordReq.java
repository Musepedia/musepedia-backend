package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "用户重置密码请求参数")
public class ResetPasswordReq {

    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
