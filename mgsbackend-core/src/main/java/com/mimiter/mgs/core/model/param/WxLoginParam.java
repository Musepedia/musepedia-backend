package com.mimiter.mgs.core.model.param;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("微信登录参数")
@Data
public class WxLoginParam extends PhoneLoginParam {

    @NotNull
    String code;

    String encryptedData;

    String iv;

    String nickname;

    String avatarUrl;
}
