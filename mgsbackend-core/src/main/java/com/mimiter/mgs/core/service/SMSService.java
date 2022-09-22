package com.mimiter.mgs.core.service;

import com.mimiter.mgs.core.model.support.SMSCode;

public interface SMSService {

    /**
     * 发送短信验证码
     * @param phone not null
     * @return 验证码对应id
     */
    SMSCode sendSMSCode(String phone);

    boolean verifyCode(String phone, String codeId, String code);

    void invalidateCode(String phone, String codeId);
}
