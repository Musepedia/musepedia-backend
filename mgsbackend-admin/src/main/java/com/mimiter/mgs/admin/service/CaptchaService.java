package com.mimiter.mgs.admin.service;


import com.mimiter.mgs.admin.model.support.Captcha;
import com.mimiter.mgs.common.exception.BadRequestException;

/**
 * 短信服务
 */
public interface CaptchaService {

    /**
     * 生成验证码，同时会存入redis
     *
     * @return 验证码对应id
     */
    Captcha generateCaptcha();


    /**
     * 校验验证码
     *
     * @param codeUuid 验证码UUID
     * @param code     验证码答案
     * @throws BadRequestException 验证码错误，不存在或过期
     */
    void verifyCaptcha(String codeUuid, String code) throws BadRequestException;


    /**
     * 使验证码失效
     *
     * @param codeUuid 验证码UUID
     */
    void invalidateCaptcha(String codeUuid);
}
