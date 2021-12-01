package cn.abstractmgs.core.model.dto;

import lombok.Data;

@Data
public class Code2SessionResponse {

    String openid;

    String session_key;

    String unionid;

    Integer errcode;

    String errmsg;
}
