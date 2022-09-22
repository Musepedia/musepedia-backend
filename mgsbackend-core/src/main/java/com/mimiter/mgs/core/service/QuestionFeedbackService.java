package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.QuestionFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QuestionFeedbackService extends IService<QuestionFeedback> {

    QuestionFeedback insertUserQuestion(Long userId, Long questionId);

    void updateFeedback(QuestionFeedback feedback);

    boolean isUserQuestionExists(Long userId, Long questionId);
}
