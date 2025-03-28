package com.mimiter.mgs.admin.model.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("验证码")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Captcha {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String code;

    @ApiModelProperty("验证码图片，base64格式")
    private String entity;

    @ApiModelProperty("验证码uuid，获取时验证码会返回，登录时需要携带")
    private String uuid;
}
