package question;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;
import com.mimiter.mgs.core.service.*;
import com.mimiter.mgs.core.utils.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@EnableCaching
public class RecommendQuestionTest {

    @Resource
    private QAService qaService;

    @Resource
    private RecommendQuestionService service;

    @Resource
    private ExhibitService exhibitService;

    @Resource
    private ExhibitTextService exhibitTextService;

    @Resource
    private ExhibitionHallService exhibitionHallService;

    @Test
    public void testUpdate() {
        service.updateQuestionFreqByText("测试问题", 1L);
    }

    @Test
    public void testInsert() {
        service.insertQuestion("测试问题2", 1, "测试答案2", 100L, null, 1L);

        // answer_type = 0表示该答案暂时无法回答
        service.insertQuestion("测试问题3", 0, null, 100L, null, 1L);
    }

    @Test
    public void testExhibitionHall() {
        System.out.println(exhibitService.selectExhibitionHallIdByExhibitId(344L));
    }

    @Test
    public void testGetRandomQuestion() {
        System.out.println(service.getRandomQuestionWithSameExhibitId(100L));
    }

    @Test
    @Transactional
    public void test2() {
        SecurityUtil.setCurrentUserId(10000L);

        String question = "狼和狗有什么关系";
        AnswerWithTextIdDTO awt = qaService.getAnswer(question, 1L);
        String answer = awt.getAnswer();
        int status = qaService.getStatus(answer);

        System.out.println(answer);
    }

    @Test
    public void test3() {
        System.out.println(exhibitTextService.getAllTexts("银杏叫什么", 1L));
    }
}
