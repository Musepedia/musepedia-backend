package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.ExhibitDTO;
import com.mimiter.mgs.core.model.dto.RecommendationDTO;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.RecommendQuestionService;
import com.mimiter.mgs.core.service.mapstruct.ExhibitDTOMapper;
import com.mimiter.mgs.core.service.mapstruct.RecommendationDTOMapper;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final RecommendQuestionService recommendQuestionService;

    private final ExhibitService exhibitService;

    private final RecommendationDTOMapper recommendationDTOMapper;

    private final ExhibitDTOMapper exhibitDTOMapper;

    @GetMapping("/exhibit")
    public BaseResponse<List<ExhibitDTO>> getRecommendExhibit(@RequestParam int count) {
        return BaseResponse.ok("ok", exhibitDTOMapper.toDto(exhibitService.getMostFrequentExhibits(count, ThreadContextHolder.getCurrentMuseumId())));
    }

    @GetMapping("/question")
    public BaseResponse<List<RecommendationDTO>> getRecommendQuestion(@RequestParam int count, @RequestParam Long exhibitId) {
        List<RecommendQuestion> recommendations = recommendQuestionService.selectMostFrequentQuestions(count, exhibitId);
        return BaseResponse.ok("ok", recommendationDTOMapper.toDto(recommendations));
    }
}
