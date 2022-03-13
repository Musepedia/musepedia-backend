package question;

import cn.abstractmgs.core.App;
import cn.abstractmgs.core.service.RecommendQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@EnableCaching
public class RecommendQuestionTest {

    @Resource
    private RecommendQuestionService service;

    @Test
    public void testUpdate() {
        service.updateQuestionFreqByText("测试问题");
    }

    @Test
    public void testInsert() {
        service.insertQuestion("测试问题2", 1, "测试答案2", 100L);

        // answer_type = 0表示该答案暂时无法回答
        service.insertQuestion("测试问题3", 0, null, 100L);
    }

    @Test
    public void testGetRandomQuestion() {
        System.out.println(service.getRandomQuestionWithSameExhibitId(100L));
    }
}
