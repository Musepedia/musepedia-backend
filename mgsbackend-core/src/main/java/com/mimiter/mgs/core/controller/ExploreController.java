package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.ExhibitDTO;
import com.mimiter.mgs.core.model.dto.RecommendationDTO;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.RecommendQuestionService;
import com.mimiter.mgs.core.service.mapstruct.ExhibitDTOMapper;
import com.mimiter.mgs.core.service.mapstruct.RecommendationDTOMapper;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("根据数量随机获取推荐问题")
    @GetMapping("/exhibit")
    public BaseResponse<List<ExhibitDTO>> getRecommendExhibit(@RequestParam int count) {
        List<Exhibit> exhibits =
                exhibitService.getMostFrequentExhibits(count, ThreadContextHolder.getCurrentMuseumId());
        return BaseResponse.ok("ok", exhibitDTOMapper.toDto(exhibits));
    }

    @ApiOperation("根据数量和展品ID获取展品相关的推荐问题")
    @GetMapping("/question")
    public BaseResponse<List<RecommendationDTO>> getRecommendQuestion(@RequestParam int count,
                                                                      @RequestParam Long exhibitId) {
        List<RecommendQuestion> recommendations =
                recommendQuestionService.selectMostFrequentQuestions(count, exhibitId);
        return BaseResponse.ok("ok", recommendationDTOMapper.toDto(recommendations));
    }
}
