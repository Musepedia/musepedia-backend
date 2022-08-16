package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.AnswerWithTextId;
import cn.abstractmgs.core.HelloRequest;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.RpcExhibitText;
import cn.abstractmgs.core.model.dto.AnswerWithTextIdDTO;
import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.*;
import cn.abstractmgs.core.utils.NLPUtil;
import cn.abstractmgs.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service("qaService")
@RequiredArgsConstructor
public class QAServiceImpl implements QAService {

    private final RecommendQuestionService recommendQuestionService;

    private final ExhibitTextService exhibitTextService;

    private final ExhibitService exhibitService;

    private final UserService userService;

    private final QuestionFeedbackService feedbackService;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public static final Pattern placeholderPattern = Pattern.compile("[\\[A-Z\\]]");

    public static final Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)\n");

    private static final String DEFAULT_ANSWER = "暂时无法回答这个问题";

    @Override
    public int getStatus(String answer) {
        if (answer.equals(DEFAULT_ANSWER)) {
            return 0;
        } else if (urlPattern.matcher(answer).matches()) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public AnswerWithTextIdDTO getAnswer(String question, Long museumId) {
        // 尝试从缓存中和数据库中获取答案
        RecommendQuestion recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);

        Long userId = SecurityUtil.getCurrentUserId();
        Long userLocation;

        if (recommendQuestion != null) {
            log.debug("getAnswer: recommend question exist {}", recommendQuestion);
            // 在数据库中更新question_freq
            recommendQuestionService.updateQuestionFreqByText(question, museumId);

            // 根据问题更新用户所在位置
            userLocation = exhibitService.selectExhibitionHallIdByExhibitId(recommendQuestion.getExhibitId());
            userService.setUserLocation(userId, userLocation);

            // 更新用户历史提问
            feedbackService.insertUserQuestion(userId, recommendQuestion.getId());

            String answerText = recommendQuestion.getAnswerType() == 0 ? DEFAULT_ANSWER : recommendQuestion.getAnswerText();
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answerText, recommendQuestion.getExhibitTextId());
        }

        // 回答类型识别与关键词分析，当回答类型=3时直接返回展品对应的图片，其余情况在分词后获取对应的text
        NLPUtil nlpUtil = new NLPUtil(question);

        int answerType = nlpUtil.answerRecognition(question);

        List<String> label = exhibitTextService.getLabel(exhibitTextService.selectAllLabelsWithAliases(museumId), question);

        // 无法从缓存或数据库中找到答案，需要经过Python模型抽取文本
        String answer = DEFAULT_ANSWER;

        if (label.size() == 0) {
            log.debug("No label found for question: {}", question);
            // 无法回答问题（无关问题）
            recommendQuestionService.insertIrrelevantQuestion(question, museumId);
            // 写入缓存
            recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, null);
        }

        List<ExhibitText> exhibitTexts = exhibitTextService.getAllTexts(question, museumId);
        List<RpcExhibitText> rpcTexts =
                exhibitTexts.stream()
                        .map(e -> RpcExhibitText.newBuilder()
                                .setText(e.getText())
                                .setId(e.getId())
                                .build())
                        .collect(Collectors.toList());

        log.debug("Question: {}, rpcTexts size: {}", question, rpcTexts.size());

        if (answerType == 3) {
            if (rpcTexts.size() != 0) {
                answer = exhibitService.selectExhibitFigureUrlByLabel(label.get(0));
                recommendQuestionService.insertQuestion(question, 3, answer, exhibitTexts.get(0).getExhibitId(), null, museumId);
            } else {
                recommendQuestionService.insertIrrelevantQuestion(question, museumId);
            }
            // 写入缓存
            recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, null);
        }

        Long textId = null;

        if (rpcTexts.size() != 0) {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(rpcTexts)
                    .setStatus(answerType)
                    .build();

            try {
                AnswerWithTextId answerWithTextId = myServiceBlockingStub
                        .sayHello(helloRequest)
                        .getAnswerWithTextId();
                String resp = answerWithTextId.getAnswer();
                log.debug("grpc response for question {}, {}", question, resp);
                // 替换grpc返回的所有包含[CLS]的占位符，如果仅包含占位符则返回"无法回答"
                resp = placeholderPattern.matcher(resp).replaceAll("");
                if (resp.length() != 0) {
                    // has exact answer
                    answer = resp;
                    textId = answerWithTextId.getTextId();
                }
            } catch (Exception e) {
                // rpc error
                log.error("Rpc error: ", e);
            }
        }

        // 根据问题更新用户所在位置
        if (exhibitTexts.size() != 0) {
            userLocation = exhibitService.selectExhibitionHallIdByExhibitId(exhibitTexts.get(0).getExhibitId());
            userService.setUserLocation(userId, userLocation);
        }

        // 将答案写入数据库中
        recommendQuestionService.insertQuestion(question,
                getStatus(answer),
                getStatus(answer) == 0 ? null : answer,
                exhibitTexts.size() == 0 ? null : exhibitTexts.get(0).getExhibitId(),
                textId,
                museumId);

        // 写入缓存后，更新用户历史提问
        recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
        feedbackService.insertUserQuestion(userId, recommendQuestion.getId());

        return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, textId);
    }
}
