package common;

import com.mimiter.mgs.common.utils.RedisUtil;
import com.mimiter.mgs.core.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CommonTest {

    @Resource
    RedisUtil redisUtil;

    @Test
    public void test1(){
        for (String key : redisUtil.keys("spring*")) {
            System.out.println(key);
        }

        redisUtil.set("testKey", "testV");
    }
}
