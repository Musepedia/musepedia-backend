package question;

import com.mimiter.mgs.admin.App;
import com.mimiter.mgs.admin.controller.QuestionController;
import com.mimiter.mgs.admin.model.dto.PageDTO;
import com.mimiter.mgs.admin.model.dto.QuestionDTO;
import com.mimiter.mgs.admin.model.query.QuestionQuery;
import com.mimiter.mgs.admin.model.request.UpdateQuestionReq;
import com.mimiter.mgs.admin.repository.QuestionRepository;
import com.mimiter.mgs.common.exception.ForbiddenException;
import com.mimiter.mgs.common.exception.ResourceNotFoundException;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import util.TestUtil;

import javax.annotation.Resource;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@ActiveProfiles("test")
@Transactional
public class QuestionTest {

    @Resource
    QuestionController questionController;

    @Resource
    QuestionRepository questionRepository;

    @Test
    public void updateQuestionTest(){
        TestUtil.loginAs(72L, STR_SYS_ADMIN);

        String answerText = System.currentTimeMillis() + "WWW";
        UpdateQuestionReq req = new UpdateQuestionReq();
        req.setId(21L);
        req.setAnswerText(answerText);
        req.setAnswerType(1);
        questionController.updateQuestion(req);

        RecommendQuestion q = questionRepository.selectById(21L);
        Assert.assertNotNull(q);
        Assert.assertEquals(answerText, q.getAnswerText());
        Assert.assertEquals(1, q.getAnswerType());

        req.setAnswerText("https://");
        req.setAnswerType(2);
        req.setExhibitId(1L);
        questionController.updateQuestion(req);

        q = questionRepository.selectById(21L);
        Assert.assertNotNull(q);
        Assert.assertEquals("https://", q.getAnswerText());
        Assert.assertEquals(2, q.getAnswerType());
        Assert.assertEquals(1L, q.getExhibitId().longValue());
    }

    @Test
    public void sysAdminUpdateQuestionMuseumNotMatchTest(){
        TestUtil.loginAs(72L, STR_SYS_ADMIN);

        String answerText = System.currentTimeMillis() + "WWW";
        UpdateQuestionReq req = new UpdateQuestionReq();
        req.setId(21L);
        req.setAnswerText(answerText);
        req.setAnswerType(1);
        req.setExhibitId(154L);
        Assert.assertThrows(IllegalArgumentException.class, () -> questionController.updateQuestion(req));
    }

    @Test
    public void museumAdminUpdateQuestionSuccessTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        String answerText = System.currentTimeMillis() + "WWWGGGGZZ__";
        UpdateQuestionReq req = new UpdateQuestionReq();
        req.setId(21L);
        req.setAnswerText(answerText);
        req.setAnswerType(1);
        req.setExhibitId(1L);
        questionController.updateQuestion(req);

        RecommendQuestion q = questionRepository.selectById(21L);
        Assert.assertNotNull(q);
        Assert.assertEquals(answerText, q.getAnswerText());
        Assert.assertEquals(1, q.getAnswerType());
        Assert.assertEquals(1L, q.getExhibitId().longValue());
    }

    @Test
    public void museumAdminUpdateQuestionNotHisMuseumTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        String answerText = System.currentTimeMillis() + "WWW";
        UpdateQuestionReq req = new UpdateQuestionReq();
        req.setId(239L);
        req.setAnswerText(answerText);
        req.setAnswerType(1);
        Assert.assertThrows("您没有权限这么做", ForbiddenException.class, () -> questionController.updateQuestion(req));
    }

    @Test
    public void museumAdminUpdateQuestionNotMuseumExhibitTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        String answerText = System.currentTimeMillis() + "WWW";
        UpdateQuestionReq req = new UpdateQuestionReq();
        req.setId(21L);
        req.setAnswerText(answerText);
        req.setAnswerType(1);
        req.setExhibitId(154L);
        Assert.assertThrows("该展品不属于您的博物馆", ForbiddenException.class, () -> questionController.updateQuestion(req));
    }

    @Test
    public void museumAdminQueryQuestionTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        QuestionQuery q1 = new QuestionQuery();
        PageDTO<QuestionDTO> list = questionController.listQuestion(q1).getData();
        Assert.assertNotNull(list);
        Assert.assertTrue(list.getData().stream().allMatch(q -> q.getMuseumId().equals(1L)));

        QuestionQuery q2 = new QuestionQuery();
        q2.setExhibitId(2L);
        PageDTO<QuestionDTO> list2 = questionController.listQuestion(q2).getData();
        Assert.assertNotNull(list2);
        Assert.assertTrue(list2.getData().stream().allMatch(q -> q.getExhibitId().equals(2L)));

        QuestionQuery q3 = new QuestionQuery();
        q3.setExhibitId(154L);
        PageDTO<QuestionDTO> list3 = questionController.listQuestion(q3).getData();
        Assert.assertNotNull(list3);
        Assert.assertTrue(list3.getData().isEmpty());

        QuestionQuery q4 = new QuestionQuery();
        q4.setQuestionText("长什么样");
        PageDTO<QuestionDTO> list4 = questionController.listQuestion(q4).getData();
        Assert.assertNotNull(list4);
        Assert.assertTrue(list4.getData().stream().allMatch(q -> q.getQuestionText().contains("长什么样")));

        QuestionQuery q5 = new QuestionQuery();
        q5.setQuestionText("寿命");
        PageDTO<QuestionDTO> list5 = questionController.listQuestion(q5).getData();
        Assert.assertNotNull(list5);
        Assert.assertTrue(list5.getData().stream().allMatch(q -> q.getQuestionText().contains("寿命")));

        QuestionQuery q6 = new QuestionQuery();
        q6.setAnswerType(1);
        PageDTO<QuestionDTO> list6 = questionController.listQuestion(q6).getData();
        Assert.assertNotNull(list6);
        Assert.assertTrue(list6.getData().stream().allMatch(q -> q.getAnswerType() == 1));
    }

    @Test
    public void museumAdminGetQuestionTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        QuestionDTO data = questionController.getQuestion(21L).getData();
        Assert.assertNotNull(data);
        Assert.assertEquals(21L, data.getId().longValue());
    }

    @Test
    public void museumAdminGetQuestionNotHisMuseumTest(){
        TestUtil.loginAs(72L, STR_MUSEUM_ADMIN);
        Assert.assertThrows(ResourceNotFoundException.class, () -> questionController.getQuestion(175L));
    }

    @Test
    public void listQuestionTest(){
        TestUtil.loginAs(72L, STR_SYS_ADMIN);
        QuestionQuery query = new QuestionQuery();
        query.setMuseumId(3L);
        query.setAnswerType(1);
        query.setSize(999);
        var res = questionController.listQuestion(query);
        Assert.assertTrue(res.getData().getData().stream().allMatch(e -> e.getAnswerType() == 1));
        Assert.assertTrue(res.getData().getData().stream().allMatch(e -> e.getMuseumId().equals(3L)));
    }
}
