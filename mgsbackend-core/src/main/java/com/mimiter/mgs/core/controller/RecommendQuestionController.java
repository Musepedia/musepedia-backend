package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.RecommendationDTO;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.RecommendQuestionService;
import com.mimiter.mgs.core.service.mapstruct.RecommendationDTOMapper;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class RecommendQuestionController {

    private final RecommendQuestionService recommendQuestionService;

    private final RecommendationDTOMapper recommendationDTOMapper;

    @GetMapping("/mine")
    public BaseResponse<List<RecommendationDTO>> getRecommendQuestion() {
        List<RecommendQuestion> recommendations = recommendQuestionService.selectNonDislikeUserQuestion(
                SecurityUtil.getCurrentUserId(), ThreadContextHolder.getCurrentMuseumId());

        return BaseResponse.ok("ok", recommendationDTOMapper.toDto(recommendations));
    }
}
