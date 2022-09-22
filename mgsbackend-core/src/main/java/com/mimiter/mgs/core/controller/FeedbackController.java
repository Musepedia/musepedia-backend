package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.QuestionFeedback;
import com.mimiter.mgs.core.service.QuestionFeedbackService;
import com.mimiter.mgs.core.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final QuestionFeedbackService questionFeedbackService;

    @ApiOperation("更新用户问题反馈")
    @PostMapping
    public BaseResponse<?> updateQuestionFeedback(@RequestBody QuestionFeedback feedback) {
        feedback.setUserId(SecurityUtil.getCurrentUserId());

        questionFeedbackService.updateFeedback(feedback);

        return BaseResponse.ok();
    }
}
