package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.ExhibitDTO;
import cn.abstractmgs.core.model.dto.RecommendationDTO;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.ExhibitService;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.mapstruct.ExhibitDTOMapper;
import cn.abstractmgs.core.service.mapstruct.RecommendationDTOMapper;
import cn.abstractmgs.core.utils.ThreadContextHolder;
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
