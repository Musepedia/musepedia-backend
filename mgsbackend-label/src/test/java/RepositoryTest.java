import com.mimiter.mgs.label.App;
import com.mimiter.mgs.label.model.entity.Paragraph;
import com.mimiter.mgs.label.model.entity.Question;
import com.mimiter.mgs.label.service.ParagraphService;
import com.mimiter.mgs.label.service.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class RepositoryTest {

    @Autowired
    QuestionService questionService;

    @Autowired
    ParagraphService paragraphService;

    @Test
    public void insertParagraph(){
        Paragraph p = new Paragraph();

        Date date = new Date(System.currentTimeMillis() - 10000_000);
        p.setCreateTime(date);

        p.setText("NIHAO");

        List<Question> questions = new ArrayList<>();
        Question q = new Question();
        q.setId(2L);
        questions.add(q);
        p.setQuestions(questions);

        paragraphService.save(p);
        System.out.println(p);
    }

    @Test
    public void insertQuestion(){
        Paragraph paragraph = new Paragraph();
        paragraph.setId(1L);
        Question q1 = new Question(null, "问题1", paragraph, null);
        Question q2 = new Question(null, "问题2", paragraph, null);
        questionService.save(q1);
        questionService.save(q2);
    }

    @Test
    public void selectParagraph(){
        Paragraph paragraph = paragraphService.getById(1L);
        System.out.println(paragraph.getText());
        List<Question> qs = paragraph.getQuestions();
        for (Question q : qs) {
            System.out.println(q.getText());
        }

    }
}
