package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.RecommendationDTO;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.mapstruct.RecommendationDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendQuestionService recommendQuestionService;

    private final RecommendationDTOMapper recommendationDTOMapper;

    @GetMapping
    public BaseResponse<List<RecommendationDTO>> getRecommendation(@RequestParam int count) {
        List<RecommendQuestion> recommendations = recommendQuestionService.selectMostFrequentQuestions(count);
        System.out.println(recommendations);

        return BaseResponse.ok("ok", recommendationDTOMapper.toDto(recommendations));
    }
}
