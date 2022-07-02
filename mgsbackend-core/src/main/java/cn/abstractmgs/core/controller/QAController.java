package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.dto.AnswerDTO;
import cn.abstractmgs.core.model.dto.AnswerWithTextIdDTO;
import cn.abstractmgs.core.model.entity.ExhibitionHall;
import cn.abstractmgs.core.recommend.RecommendExhibitionHallService;
import cn.abstractmgs.core.service.*;
import cn.abstractmgs.core.utils.SecurityUtil;
import cn.abstractmgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    @GetMapping
    public BaseResponse<AnswerDTO> getAnswer(@RequestParam String question) {
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        AnswerWithTextIdDTO awt = qaService.getAnswer(question, museumId);
        String answer = awt.getAnswer();
        int status = qaService.getStatus(answer);

        int countOfRecommendation = 2;
        List<String> recommendQuestions;
        try {
            recommendQuestions = recommendQuestionService.selectRecommendQuestions(question, answer, museumId);
        } catch (Exception ex) {
            // TODO 当推荐算法抛出异常时，使用随机推荐代替
            log.error("推荐算法异常：", ex);
            recommendQuestions = recommendQuestionService.getRandomQuestions(countOfRecommendation);
        }

        ExhibitionHall recommendExhibitionHall = null;
        if (userService.isUserAtEndOfExhibitionHall(SecurityUtil.getCurrentUserId())) {
            Long currentLocationId = userService.getUserLocation(SecurityUtil.getCurrentUserId());
            ExhibitionHall currentLocation = exhibitionHallService.getById(currentLocationId);
            List<ExhibitionHall> userPref = userPreferenceService.getPreferredHallByUserId(SecurityUtil.getCurrentUserId(), museumId);
            recommendExhibitionHall = recommendExhibitionHallService.getRecommendExhibitionHall(museumId, userPref, currentLocation);
        }
        return BaseResponse.ok(new AnswerDTO(status, answer, awt.getTextId(), recommendQuestions, recommendExhibitionHall));
    }
}