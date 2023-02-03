package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "更新用户请求参数")
public class UpdateUserReq {

    private Long id;

    private String username;

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
