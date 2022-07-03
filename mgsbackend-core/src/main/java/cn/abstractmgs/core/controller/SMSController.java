package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.common.exception.BadRequestException;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.support.SMSCode;
import cn.abstractmgs.core.service.SMSService;
import cn.abstractmgs.core.utils.RedisUtil;
import cn.abstractmgs.core.utils.RequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@RequiredArgsConstructor
public class SMSController {

    private final SMSService smsService;

    private final RedisUtil redisUtil;

    @ApiOperation("获取短信验证码")
    @GetMapping
    @AnonymousAccess
    public BaseResponse<?> sendSMS(String phone, HttpServletRequest request){
        String ip = RequestUtil.getIp(request);
        String key = "limit:sms:" + ip;

        if(redisUtil.get(key) != null){
            throw new BadRequestException("请求过于频繁，请稍后再试");
        }
        SMSCode code = smsService.sendSMSCode(phone);
        redisUtil.set(key, 1, 1, TimeUnit.MINUTES);
        return BaseResponse.ok("ok", code.getCodeId());
    }
}
