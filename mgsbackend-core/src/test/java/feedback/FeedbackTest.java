package feedback;

import com.mimiter.mgs.core.App;
import com.mimiter.mgs.core.service.QuestionFeedbackService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class FeedbackTest {

    @Resource
    private QuestionFeedbackService feedbackService;

    @Test
    public void insertDuplicatedUserQuestions() {
        for (int i=0; i<10; ++i) {
            feedbackService.insertUserQuestion(10000L, 201L);
        }
    }
}