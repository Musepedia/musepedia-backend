package common;

import com.mimiter.mgs.common.utils.RedisUtil;
import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.model.support.SMSCode;
import com.mimiter.mgs.core.service.SMSService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
public class CommonTest {

    @Resource
    RedisUtil redisUtil;

    @Resource
    SMSService smsService;

    @Test
    public void test1(){
        for (String key : redisUtil.keys("spring*")) {
            System.out.println(key);
        }

        redisUtil.set("testKey", "testV");
    }

    @Test
    @Disabled
    public void sendSms(){
        SMSCode code = smsService.sendSMSCode("15821243161");
        Assert.assertNotNull(code);
    }
}
