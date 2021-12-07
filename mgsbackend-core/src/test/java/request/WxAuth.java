package request;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.model.param.WxLoginParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class WxAuth {

    @Resource
    RestTemplate restTemplate;

    @Test
    public void test(){
        String iv = "N1t3uTDl3I3xRNCn/LM7bg==";
        String encryptedData = "lrDmQD4uzrm4rpTh0GT8GbmOfLQC06l1lGE0IjsCzd8QeNuEXR2juYHz90kpidDv+CloD7NytAQYAk5WobWAQhOvk5GXMEtvXZmgkFnhzrUsM1iCAVCo/l6SZPMbZvQNTwh8zjKxp4hbwUa/O+s9a/I2t1bjGTmF/vE0q0L/+faVM3P72V2EFCeNquZWln9Nc+mxEmsmHVxJUHA1hKjxHU8jEu76imGSRQC1F9a8bMWUMXreubIuAetHFxmGzYtm5HBVaExgzrxsMfbALSc1XdJz2TpTzM8Pe7J+13iz0MAGBkxY/dDLz0sAliwy7wLbJG0dO0i1IvKfszFTZx9Ml6ww/DmQPtU/kBsVD8KHZseMGfvqm7rIbgrzD39+2QSxv1Bk9As1X96DOV14V/3hqA==";
        WxLoginParam param = new WxLoginParam();
        param.setIv(iv);
        param.setEncryptedData(encryptedData);


    }
}
