package request;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.model.dto.Code2SessionResponse;
import cn.abstractmgs.core.utils.CipherUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class WxAuth {

    @Resource
    RestTemplate restTemplate;

    @Test
    public void test(){
        String iv = "N1t3uTDl3I3xRNCn/LM7bg==";
        String encryptedData = "lrDmQD4uzrm4rpTh0GT8GbmOfLQC06l1lGE0IjsCzd8QeNuEXR2juYHz90kpidDv+CloD7NytAQYAk5WobWAQhOvk5GXMEtvXZmgkFnhzrUsM1iCAVCo/l6SZPMbZvQNTwh8zjKxp4hbwUa/O+s9a/I2t1bjGTmF/vE0q0L/+faVM3P72V2EFCeNquZWln9Nc+mxEmsmHVxJUHA1hKjxHU8jEu76imGSRQC1F9a8bMWUMXreubIuAetHFxmGzYtm5HBVaExgzrxsMfbALSc1XdJz2TpTzM8Pe7J+13iz0MAGBkxY/dDLz0sAliwy7wLbJG0dO0i1IvKfszFTZx9Ml6ww/DmQPtU/kBsVD8KHZseMGfvqm7rIbgrzD39+2QSxv1Bk9As1X96DOV14V/3hqA==";
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=wxfe9a49b9f26c289d" +
                "&secret=60232f676e1215a24a8e357d2b1c76dc" +
                "&js_code=" + "003POH000kdWRM12lH2002OZyS0POH0H" +
                "&grant_type=authorization_code";
        Code2SessionResponse resp = restTemplate.getForObject(url , Code2SessionResponse.class, new HashMap<>());
        System.out.println(resp.getOpenid());
        String phoneNumber = CipherUtil.decryptPhoneNumber(resp.getSession_key(), encryptedData, iv);

    }
}
