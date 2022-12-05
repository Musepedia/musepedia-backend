package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@ApiModel(value = "添加用户请求参数")
@Data
public class AddUserReq {

    private String username;

    private String password;

    private String email;

    private String phone;

    @ApiModelProperty(value = "称呼")
    private String nickname;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIds;

    @ApiModelProperty(value = "如果是博物馆/学校管理员，需要填写博物馆/学校ID",
            notes = "假设一位用户不会同时是博物馆和学校管理员")
    private Long institutionId;
}
