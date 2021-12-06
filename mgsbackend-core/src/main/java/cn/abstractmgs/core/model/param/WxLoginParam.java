package cn.abstractmgs.core.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WxLoginParam {

    @NotNull
    String code;

    @NotNull
    String encryptedData;

    @NotNull
    String iv;

    @NotNull
    String nickname;

    @NotNull
    String avatarUrl;
}
