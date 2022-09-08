package cn.abstractmgs.core.service;

import cn.abstractmgs.model.entity.QuestionFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

public interface QuestionFeedbackService extends IService<QuestionFeedback> {

    QuestionFeedback insertUserQuestion(Long userId, Long questionId);

    void updateFeedback(QuestionFeedback feedback);

    boolean isUserQuestionExists(Long userId, Long questionId);
}
