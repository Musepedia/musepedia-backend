package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.param.FeedbackParam;
import com.mimiter.mgs.core.repository.GPTCompletionRepository;
import com.mimiter.mgs.core.repository.OpenQAQuestionRepository;
import com.mimiter.mgs.model.entity.GPTCompletion;
import com.mimiter.mgs.model.entity.OpenQAQuestion;
import com.mimiter.mgs.model.entity.QuestionFeedback;
import com.mimiter.mgs.core.service.QuestionFeedbackService;
import com.mimiter.mgs.core.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.mimiter.mgs.core.service.impl.QAServiceImpl.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final QuestionFeedbackService questionFeedbackService;

    private final OpenQAQuestionRepository qaQuestionRepository;

    private final GPTCompletionRepository gptCompletionRepository;

    @ApiOperation("更新用户问题反馈")
    @PostMapping
    public BaseResponse<?> updateQuestionFeedback(@RequestBody @Validated FeedbackParam req) {
        Long userId = SecurityUtil.getCurrentUserId();
        switch (req.getQaType()) {
        case QA_TYPE_DEFAULT:
            QuestionFeedback feedback = new QuestionFeedback();
            feedback.setFeedback(req.getFeedback());
            feedback.setUserId(userId);
            feedback.setQuestionId(req.getQuestionId());
            questionFeedbackService.updateFeedback(feedback);
            break;
        case QA_TYPE_OPEN_QA:
            OpenQAQuestion qaQuestion = new OpenQAQuestion();
            qaQuestion.setId(req.getQuestionId());
            qaQuestion.setFeedback(req.getFeedback());
            qaQuestionRepository.updateById(qaQuestion);
            break;
        case QA_TYPE_GPT:
            GPTCompletion completion = new GPTCompletion();
            completion.setId(req.getQuestionId());
            completion.setFeedback(req.getFeedback());
            gptCompletionRepository.updateById(completion);
            break;
        default:
        }

        return BaseResponse.ok();
    }
}
