package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.QuestionFeedback;
import com.mimiter.mgs.core.repository.QuestionFeedbackRepository;
import com.mimiter.mgs.core.service.QuestionFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("questionFeedbackService")
public class QuestionFeedbackServiceImpl
        extends ServiceImpl<QuestionFeedbackRepository, QuestionFeedback>
        implements QuestionFeedbackService {

    @Override
    public QuestionFeedback insertUserQuestion(Long userId, Long questionId) {
        QuestionFeedback feedback = new QuestionFeedback(null, userId, questionId, null);
        if (!isUserQuestionExists(userId, questionId)) {
            save(feedback);
        }
        return feedback;
    }

    @Override
    public void updateFeedback(QuestionFeedback feedback) {
        getBaseMapper().updateUserFeedbackOnQuestion(feedback.getUserId(), feedback.getQuestionId(), feedback.getFeedback());
    }

    @Override
    public boolean isUserQuestionExists(Long userId, Long questionId) {
        return baseMapper.selectUserQuestion(userId, questionId) != null;
    }
}
