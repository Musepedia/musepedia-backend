package com.mimiter.mgs.core.model.param;

import lombok.Data;

/**
 * 手机号登录参数
 */
@Data
public class PhoneLoginParam {

    String phoneNumber;

    /**
     * 短信验证码
     */
    String sms;

    /**
     * 后端随机生成的UUID，用于验证短信验证码
     */
    String vc;
}
