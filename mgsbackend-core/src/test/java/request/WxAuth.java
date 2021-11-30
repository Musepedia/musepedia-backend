package request;

import cn.abstractmgs.core.model.dto.WxUser;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class WxAuth {

    @Test
    public void test(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> param = new HashMap<>();
        param.put("appid", "wxfe9a49b9f26c289d");
        param.put("secret", "60232f676e1215a24a8e357d2b1c76dc");
        param.put("js_code", "073prI10057pQM1WOY300aELhD4prI1M");
        param.put("grant_type", "authorization_code");
        WxUser wxUser = restTemplate.getForObject("https://api.weixin.qq.com/sns/jscode2session", WxUser.class, param);
    }
}
