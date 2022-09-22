package com.mimiter.mgs.core.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WxLoginParam extends PhoneLoginParam {

    @NotNull
    String code;

    String encryptedData;

    String iv;

    String nickname;

    String avatarUrl;
}
