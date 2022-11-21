package com.mimiter.mgs.admin.model.request;

import io.swagger.annotations.ApiModel;
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

    @ApiParam(value = "称呼")
    private String nickname;

    private List<Long> roleIds;

}
