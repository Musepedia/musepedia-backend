package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.support.Captcha;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.utils.RedisUtil;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("captchaService")
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {

    public static final String CAPTCHA_PREFIX = "mgs_captcha:";

    public static final int CAPTCHA_EXPIRE_MINUTES = 5;

    public static final int CAPTCHA_LENGTH = 2;

    public static final int CAPTCHA_WIDTH = 120;

    public static final int CAPTCHA_HEIGHT = 32;

    private final RedisUtil redisUtil;

    @Override
    public Captcha generateCaptcha() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, CAPTCHA_LENGTH);
        String verCode = captcha.text().toLowerCase();
        if (verCode.contains(".")) {
            verCode = verCode.split("\\.")[0];
        }
        String uuid = UUID.randomUUID().toString();
        redisUtil.set(CAPTCHA_PREFIX + uuid, verCode, CAPTCHA_EXPIRE_MINUTES, TimeUnit.MINUTES);
        return new Captcha(verCode, captcha.toBase64(), uuid);
    }

    @Override
    public void verifyCaptcha(String codeUuid, String code) throws BadRequestException {
        Assert.notNull(codeUuid, "验证码ID不能为空");
        Assert.notNull(code, "验证码不能为空");

        String actualCode = (String) redisUtil.get(CAPTCHA_PREFIX + codeUuid);
        redisUtil.del(CAPTCHA_PREFIX + codeUuid);
        if (Strings.isBlank(actualCode)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (Strings.isBlank(code) || !code.equalsIgnoreCase(actualCode)) {
            throw new BadRequestException("验证码错误");
        }
    }

    @Override
    public void invalidateCaptcha(String codeUuid) {
        Assert.isTrue(Strings.isNotBlank(codeUuid), "验证码ID不能为空");

        redisUtil.del(CAPTCHA_PREFIX + codeUuid);
    }
}
