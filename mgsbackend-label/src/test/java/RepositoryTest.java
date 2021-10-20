import cn.abstractmgs.label.App;
import cn.abstractmgs.label.model.entity.Paragraph;
import cn.abstractmgs.label.model.entity.Question;
import cn.abstractmgs.label.repository.QuestionRepository;
import cn.abstractmgs.label.service.ParagraphService;
import cn.abstractmgs.label.service.QuestionService;
import cn.abstractmgs.label.service.impl.QuestionServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
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
        p.setText("NIHAO");
        paragraphService.save(p);
        System.out.println(p);
    }

    @Test
    public void insertQuestion(){
        Paragraph paragraph = paragraphService.getById(1L);
        Question question = new Question();
        question.setText("你好吗？");
        question.setParagraph(paragraph);
        questionService.save(question);
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
