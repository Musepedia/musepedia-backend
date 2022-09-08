package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.RecommendationDTO;
import cn.abstractmgs.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.mapstruct.RecommendationDTOMapper;
import cn.abstractmgs.core.utils.SecurityUtil;
import cn.abstractmgs.core.utils.ThreadContextHolder;
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
