package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.QuestionFeedback;
import cn.abstractmgs.core.repository.QuestionFeedbackRepository;
import cn.abstractmgs.core.service.QuestionFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("questionFeedbackService")
public class QuestionFeedbackServiceImpl
        extends ServiceImpl<QuestionFeedbackRepository, QuestionFeedback>
        implements QuestionFeedbackService {

    @Override
    public QuestionFeedback insertUserQuestion(Long userId, Long questionId) {
        QuestionFeedback feedback = new QuestionFeedback(null, userId, questionId, null);
        save(feedback);
        return feedback;
    }

    @Override
    public void updateFeedback(QuestionFeedback feedback) {
        getBaseMapper().updateUserFeedbackOnQuestion(feedback.getUserId(), feedback.getQuestionId(), feedback.getFeedback());
    }
}
