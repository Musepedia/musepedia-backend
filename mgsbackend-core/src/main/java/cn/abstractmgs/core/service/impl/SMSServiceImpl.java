package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.common.exception.InternalException;
import cn.abstractmgs.core.model.support.SMSCode;
import cn.abstractmgs.core.service.SMSService;
import cn.abstractmgs.common.utils.RedisUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
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

    private final RedisUtil redisUtil;

    private static final String CODE_PREFIX = "SMSCODE:";

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");

    private final Client smsClient;

    @Override
    public SMSCode sendSMSCode(String phone) {
        Assert.notNull(phone, "手机号不能为空");
        Assert.isTrue(PHONE_PATTERN.matcher(phone).matches(), "手机号格式不正确");

        SMSCode code = generateAndSetCode(phone);

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(phone)
                .setTemplateParam("{\"code\":\"" + code.getCode() + "\"}");
        try {
            smsClient.sendSms(sendSmsRequest);
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
        boolean res = correctCode != null && correctCode.equals(code);
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
        redisUtil.set(getRedisCodeKey(phone, codeId), verCode, 30, TimeUnit.MINUTES);
        return new SMSCode(verCode, codeId);
    }

    private String getRedisCodeKey(String phone, String codeId){
        return CODE_PREFIX + phone + ":" + codeId;
    }
}
