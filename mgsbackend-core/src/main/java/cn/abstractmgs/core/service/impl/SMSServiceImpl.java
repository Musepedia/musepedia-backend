package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.support.SMSCode;
import cn.abstractmgs.core.service.SMSService;
import cn.abstractmgs.core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service("smsService")
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService {

    private final RedisUtil redisUtil;

    private static final String CODE_PREFIX = "SMSCODE:";

    private static final String SMS_TEMPLATE = "您的验证码为{}，有效期{}分钟";

    @Override
    public SMSCode sendSMSCode(String phone) {

        SMSCode code = generateAndSetCode(phone);

        // TODO SEND SMS

        return code;
    }

    @Override
    public boolean verifyCode(String phone, String codeId, String code){
        String correctCode = (String) redisUtil.get(getRedisCodeKey(phone, codeId));
        return correctCode != null && correctCode.equals(code);
    }

    @Override
    public void invalidateCode(String phone, String codeId){
        redisUtil.del(getRedisCodeKey(phone, codeId));
    }

    private SMSCode generateAndSetCode(String phone){
        String verCode = StringUtils.leftPad(new Random().nextInt(100000)+"", 6);
        String codeId = UUID.randomUUID().toString().replaceAll("-", "");
        redisUtil.set(getRedisCodeKey(phone, codeId), verCode, 30, TimeUnit.MINUTES);
        return new SMSCode(verCode, codeId);
    }

    private String getRedisCodeKey(String phone, String codeId){
        return CODE_PREFIX + phone + ":" + codeId;
    }
}
