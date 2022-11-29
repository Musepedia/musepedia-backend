package com.mimiter.mgs.admin.model.dto;

import com.mimiter.mgs.admin.model.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("用户信息")
@Data
public class UserDTO {

    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("称呼")
    private String nickname;

    private String email;

    private String phone;

    private List<Role> roles;
}
