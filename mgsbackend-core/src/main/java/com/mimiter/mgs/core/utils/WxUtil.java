package com.mimiter.mgs.core.utils;

import com.mimiter.mgs.core.config.WeixinMpConfig;
import com.mimiter.mgs.core.model.response.Code2SessionResponse;
import com.mimiter.mgs.core.model.param.WxLoginParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * 微信小程序相关工具类
 */
@Component
@RequiredArgsConstructor
public class WxUtil {

    private final RestTemplate restTemplate;

    private final WeixinMpConfig config;

    /**
     * 通过小程序前端获取的jscode向微信请求Session相关信息
     */
    public Code2SessionResponse code2Session(WxLoginParam param) {
        Assert.notNull(param, "WxLoginParam不能为空");
        String url = "https://api.weixin.qq.com/sns/jscode2session?"
                + "appid=" + config.getAppid()
                + "&secret=" + config.getSecret()
                + "&js_code=" + param.getCode()
                + "&grant_type=authorization_code";
        return restTemplate.getForObject(url, Code2SessionResponse.class, new HashMap<>());
    }
}
