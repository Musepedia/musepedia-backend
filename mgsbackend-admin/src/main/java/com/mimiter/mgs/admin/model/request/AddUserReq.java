package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@ApiModel(value = "添加用户请求参数")
@Data
public class AddUserReq {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @ApiModelProperty(value = "称呼")
    private String nickname;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIds;

    @ApiModelProperty(value = "如果是博物馆/学校管理员，需要填写博物馆/学校ID",
            notes = "假设一位用户不会同时是博物馆和学校管理员")
    private Long institutionId;
}
