package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.common.exception.InternalException;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import com.mimiter.mgs.core.config.SMSConfig;
import com.mimiter.mgs.core.model.support.SMSCode;
import com.mimiter.mgs.core.service.SMSService;
import com.mimiter.mgs.common.utils.RedisUtil;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Slf4j
@Service("smsService")
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService {

    private static final String CODE_PREFIX = "SMSCODE:";

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");

    private static final int CODE_EXPIRE_MINUTES = 10;

    private final RedisUtil redisUtil;

    private final SmsClient smsClient;

    private final SMSConfig smsConfig;

    @Override
    public SMSCode sendSMSCode(String phone) {
        Assert.notNull(phone, "手机号不能为空");
        Assert.isTrue(PHONE_PATTERN.matcher(phone).matches(), "手机号格式不正确或不支持");
        if (EnvironmentUtil.isTestEnv()) {
            String verCode = "102030";
            String codeId = UUID.randomUUID().toString().replaceAll("-", "");
            redisUtil.set(getRedisCodeKey(phone, codeId), verCode, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
            return new SMSCode(verCode, codeId);
        }

        SMSCode code = generateAndSetCode(phone);

        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppId(smsConfig.getSdkAppId());
        req.setSignName(smsConfig.getSignName());
        req.setTemplateId(smsConfig.getTemplateId());
        /*
         * {1}为您的登录验证码，请于{2}分钟内填写，如非本人操作，请忽略本短信。
         */
        String[] templateParamSet = {code.getCode(), CODE_EXPIRE_MINUTES + ""};
        req.setTemplateParamSet(templateParamSet);
        String[] phoneNumberSet = {"+86" + phone};
        req.setPhoneNumberSet(phoneNumberSet);

        try {
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = smsClient.SendSms(req);
            for (SendStatus sendStatus : res.getSendStatusSet()) {
                if (!"Ok".equals(sendStatus.getCode())) {
                    throw new InternalException("服务器短信发送异常，请稍后重试");
                }
            }
            log.info("短信发送成功，手机号：{}，返回结果：{}", phone, SendSmsResponse.toJsonString(res));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new InternalException("服务器短信发送异常，请稍后重试");
        }

        return code;
    }

    @Override
    public boolean verifyCode(String phone, String codeId, String code){
        if(phone == null || codeId == null || code == null){
            return false;
        }
        String correctCode = (String) redisUtil.get(getRedisCodeKey(phone, codeId));
        boolean res = code.equals(correctCode);
        if(res) {
            invalidateCode(phone, codeId);
        }
        return res;
    }

    @Override
    public void invalidateCode(String phone, String codeId){
        redisUtil.del(getRedisCodeKey(phone, codeId));
    }

    private SMSCode generateAndSetCode(String phone){
        String verCode = StringUtils.leftPad(new Random().nextInt(1000000)+"", 6, "0");
        String codeId = UUID.randomUUID().toString().replaceAll("-", "");
        redisUtil.set(getRedisCodeKey(phone, codeId), verCode, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        return new SMSCode(verCode, codeId);
    }

    private String getRedisCodeKey(String phone, String codeId){
        return CODE_PREFIX + phone + ":" + codeId;
    }
}
