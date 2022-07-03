package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.common.exception.BadRequestException;
import cn.abstractmgs.core.model.support.SMSCode;
import cn.abstractmgs.core.service.SMSService;
import cn.abstractmgs.core.utils.RedisUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.AddSmsTemplateRequest;
import com.aliyun.dysmsapi20170525.models.AddSmsTemplateResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service("smsService")
@RequiredArgsConstructor
public class SMSServiceImpl implements SMSService {

    private final RedisUtil redisUtil;

    private static final String CODE_PREFIX = "SMSCODE:";

    private static final String SMS_TEMPLATE = "您的验证码为{}，有效期{}分钟";

    private static final Pattern PHONE_PATTERN = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");

    private final Client smsClient;

    @Override
    public SMSCode sendSMSCode(String phone) {
        Assert.notNull(phone, "手机号不能为空");

        SMSCode code = generateAndSetCode(phone);

        if(code != null) {
            // test only
            return code;
        }
        SendSmsRequest request = new SendSmsRequest()
                .setTemplateCode("")
                .setTemplateParam("");
        try{
            // 复制代码运行请自行打印API的返回值
            smsClient.sendSms(request);
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
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
