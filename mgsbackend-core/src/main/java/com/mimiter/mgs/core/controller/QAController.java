package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.MyServiceGrpc;
import com.mimiter.mgs.core.model.dto.AnswerDTO;
import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.recommend.RecommendExhibitionHallService;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mimiter.mgs.core.service.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QAController {

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    private final RecommendQuestionService recommendQuestionService;

    private final RecommendExhibitionHallService recommendExhibitionHallService;

    private final ExhibitionHallService exhibitionHallService;

    private final UserPreferenceService userPreferenceService;

    private final QAService qaService;

    private final UserService userService;

    private static final int DEFAULT_RECOMMENDATION_COUNT = 2;

    @ApiOperation("QA提问接口，获取问题的回答")
    @GetMapping
    public BaseResponse<AnswerDTO> getAnswer(@RequestParam String question) throws JsonProcessingException {
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        Long userId = SecurityUtil.getCurrentUserId();
        AnswerWithTextIdDTO awt = qaService.getAnswer(question, museumId);

        List<String> recommendQuestions;
        try {
            recommendQuestions = recommendQuestionService.selectRecommendQuestions(question, museumId);
        } catch (Exception ex) {
            // 当推荐算法抛出异常时，使用随机推荐代替
            log.error("推荐算法异常：", ex);
            recommendQuestions = recommendQuestionService.getRandomQuestions(
                    DEFAULT_RECOMMENDATION_COUNT,
                    ThreadContextHolder.getCurrentMuseumId());
        }

        ExhibitionHall recommendExhibitionHall = null;
        try {
            if (recommendExhibitionHallService.isRecommendExhibitionHall(SecurityUtil.getCurrentUserId())) {
                Long currentLocationId = userService.getUserLocation(userId);
                ExhibitionHall currentLocation = exhibitionHallService.getById(currentLocationId);
                List<ExhibitionHall> userPref = userPreferenceService.getPreferredHallByUserId(userId, museumId);
                recommendExhibitionHall = recommendExhibitionHallService
                        .getRecommendExhibitionHall(museumId, userPref, currentLocation);

                // 直到用户位置发生变化为止，不再向该用户推荐展区
                userService.setUserRecommendStatus(userId, false);
            }
        } catch (Exception e) {
            log.error("推荐展厅异常：", e);
        }
        return BaseResponse.ok(new AnswerDTO(
                awt.getAnswerType(), awt.getQuestionId(), awt.getAnswer(), awt.getTextId(),
                recommendQuestions, recommendExhibitionHall));
    }
}