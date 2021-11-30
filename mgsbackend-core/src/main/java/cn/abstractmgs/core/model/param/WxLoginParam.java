package cn.abstractmgs.core.model.param;

import lombok.Data;

@Data
public class WxLoginParam {

    String code;

    String authorizationCode;

    String encryptedData;

    String iv;

    String nickname;

    String avatar;
}
