package com.mimiter.mgs.admin.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "登录请求参数")
@Data
public class LoginParam {

    private String username;

    private String email;

    private String password;

    private String captcha;

    /**
     * 验证码uuid
     */
    private String uuid;
}
