package com.mimiter.mgs.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.RecommendationDTO;
import com.mimiter.mgs.core.repository.GPTCompletionRepository;
import com.mimiter.mgs.core.repository.OpenQAQuestionRepository;
import com.mimiter.mgs.model.entity.GPTCompletion;
import com.mimiter.mgs.model.entity.OpenQAQuestion;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.RecommendQuestionService;
import com.mimiter.mgs.core.service.mapstruct.RecommendationDTOMapper;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.mimiter.mgs.core.service.impl.QAServiceImpl.*;

@Slf4j
@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final RecommendQuestionService recommendQuestionService;

    private final RecommendationDTOMapper recommendationDTOMapper;

    private final GPTCompletionRepository gptCompletionRepository;

    private final OpenQAQuestionRepository openQAQuestionRepository;

    @ApiOperation("获取用户当前博物馆的历史提问，不包括负面反馈的问题")
    @GetMapping("/mine")
    public BaseResponse<List<RecommendationDTO>> getHistoryQuestionsInCurrentMuseum() {
        Long useId = SecurityUtil.getCurrentUserId();
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        List<RecommendQuestion> recommendations = recommendQuestionService
                .selectNonDislikeUserQuestion(useId, museumId);

        final var res = recommendationDTOMapper.toDto(recommendations);

        QueryWrapper<OpenQAQuestion> q1 = new QueryWrapper<>();
        q1.eq("user_id", useId);
        q1.eq("museum_id", museumId);
        q1.and(w -> {
            w.isNull("feedback");
            w.or().eq("feedback", 0);
        });
        var qa = openQAQuestionRepository.selectList(q1);
        res.addAll(openQAToDto(qa));

        QueryWrapper<GPTCompletion> q2 = new QueryWrapper<>();
        q2.eq("user_id", useId);
        q2.eq("museum_id", museumId);
        q2.and(w -> {
            w.isNull("feedback");
            w.or().eq("feedback", 0);
        });
        var gpt = gptCompletionRepository.selectList(q2);
        res.addAll(gptToDto(gpt));

        return BaseResponse.ok("ok", res);
    }

    private List<RecommendationDTO> openQAToDto(List<OpenQAQuestion> qa) {
        List<RecommendationDTO> res = new ArrayList<>();
        for (var q : qa) {
            RecommendationDTO r = new RecommendationDTO();
            r.setQuestionId(q.getId());
            r.setQuestionText(q.getQuestion());
            r.setAnswerText(q.getAnswer());
            r.setAnswerType(TYPE_TEXT_ANSWER);
            r.setQaType(QA_TYPE_OPEN_QA);
            res.add(r);
        }
        return res;
    }

    private List<RecommendationDTO> gptToDto(List<GPTCompletion> qa) {
        List<RecommendationDTO> res = new ArrayList<>();
        for (var q : qa) {
            RecommendationDTO r = new RecommendationDTO();
            r.setQuestionId(q.getId());
            r.setQuestionText(q.getUserQuestion());
            r.setAnswerText(q.getCompletion());
            r.setAnswerType(TYPE_TEXT_ANSWER);
            r.setQaType(QA_TYPE_GPT);
            res.add(r);
        }
        return res;
    }

}
