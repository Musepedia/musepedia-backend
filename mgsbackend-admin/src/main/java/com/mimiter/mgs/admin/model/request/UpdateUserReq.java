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
}
