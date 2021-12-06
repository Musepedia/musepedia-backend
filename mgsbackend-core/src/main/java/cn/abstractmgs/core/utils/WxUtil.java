package cn.abstractmgs.core.utils;

import cn.abstractmgs.core.config.WeixinMpConfig;
import cn.abstractmgs.core.model.response.Code2SessionResponse;
import cn.abstractmgs.core.model.param.WxLoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class WxUtil {

    private final RestTemplate restTemplate;

    private final WeixinMpConfig config;

    public Code2SessionResponse code2Session(WxLoginParam param){
        Assert.notNull(param, "WxLoginParam不能为空");
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + config.getAppid() +
                "&secret=" + config.getSecret() +
                "&js_code=" + param.getCode() +
                "&grant_type=authorization_code";
        return restTemplate.getForObject(url , Code2SessionResponse.class, new HashMap<>());
//        String iv = param.getIv();
//        String encryptedData = param.getEncryptedData();
//        String phoneNumber = CipherUtil.decryptPhoneNumber(resp.getSession_key(), encryptedData, iv);

    }
}
