package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.model.entity.QuestionFeedback;
import cn.abstractmgs.core.service.QuestionFeedbackService;
import cn.abstractmgs.core.utils.SecurityUtil;
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
