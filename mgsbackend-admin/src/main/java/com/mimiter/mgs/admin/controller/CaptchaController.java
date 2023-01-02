package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.model.support.Captcha;
import com.mimiter.mgs.admin.service.CaptchaService;
import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
@Api(value = "验证码API", tags = {"验证码API"})
public class CaptchaController {

    private final CaptchaService captchaService;

    @ApiOperation(value = "获取验证码",
            notes = "验证码五分钟失效，使用验证码登录无论成功与否都会失效，需要重新获取验证码")
    @GetMapping
    @AnonymousAccess
    public BaseResponse<Captcha> getCaptcha() {
        return BaseResponse.ok(captchaService.generateCaptcha());
    }
}
