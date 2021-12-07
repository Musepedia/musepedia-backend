package cn.abstractmgs.core.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WxLoginParam {

    @NotNull
    String code;

    String encryptedData;

    String iv;

    String nickname;

    String avatarUrl;
}
