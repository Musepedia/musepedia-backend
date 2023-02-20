package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.core.AnswerWithTextId;
import com.mimiter.mgs.core.HelloRequest;
import com.mimiter.mgs.core.MyServiceGrpc;
import com.mimiter.mgs.core.RpcExhibitText;
import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.*;
import com.mimiter.mgs.core.utils.NLPUtil;
import com.mimiter.mgs.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
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

    private final NLPUtil nlpUtil;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[\\[A-Z\\]]");

    public static final Pattern URL_PATTERN = Pattern.compile(
            "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(.*)");

    private static final String DEFAULT_ANSWER_TEXT = "暂时无法回答这个问题";

    public static final int TYPE_DEFAULT_ANSWER = 0;

    public static final int TYPE_TEXT_ANSWER = 1;

    public static final int TYPE_IMAGE_ANSWER = 2;

    private int getAnswerType(String answer) {
        if (answer.equals(DEFAULT_ANSWER_TEXT)) {
            return TYPE_DEFAULT_ANSWER;
        } else if (URL_PATTERN.matcher(answer).matches()) {
            return TYPE_IMAGE_ANSWER;
        } else {
            return TYPE_TEXT_ANSWER;
        }
    }

    @Override
    @SuppressWarnings("MethodLength") // 历史遗留问题
    public AnswerWithTextIdDTO getAnswer(String question, Long museumId) {
        // 尝试从缓存中和数据库中获取答案
        RecommendQuestion recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);

        Long userId = SecurityUtil.getCurrentUserId();

        if (recommendQuestion != null) {
            log.debug("getAnswer: recommend question exist {}", recommendQuestion);
            // 在数据库中更新question_freq
            recommendQuestionService.updateQuestionFreqByText(question, museumId);

            // 根据问题更新用户所在位置
            Long userLocation = exhibitService.selectExhibitionHallIdByExhibitId(recommendQuestion.getExhibitId());
            if (userLocation != null) {
                userService.setUserLocation(userId, userLocation);
            }

            // 更新用户历史提问
            feedbackService.insertUserQuestion(userId, recommendQuestion.getId());

            String answerText = recommendQuestion.getAnswerType() == 0
                    ? DEFAULT_ANSWER_TEXT
                    : recommendQuestion.getAnswerText();
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answerText,
                    getAnswerType(answerText), recommendQuestion.getExhibitTextId(), recommendQuestion.getExhibitId());
        }

        // 回答类型识别与关键词分析，当回答类型=3时直接返回展品对应的图片，其余情况在分词后获取对应的text
        List<String> label = exhibitTextService.getLabel(
                exhibitTextService.selectAllLabelsWithAliases(museumId), question);

        // 无法从缓存或数据库中找到答案，需要经过Python模型抽取文本
        String answer = DEFAULT_ANSWER_TEXT;

        if (label.size() == 0) {
            log.debug("No label found for question: {}", question);
            // 无法回答问题（无关问题）
            recommendQuestionService.insertIrrelevantQuestion(question, museumId);
            // 写入缓存
            recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, TYPE_DEFAULT_ANSWER, null, null);
        }

        List<ExhibitText> exhibitTexts = exhibitTextService.getAllTexts(question, museumId);

        int questionType = nlpUtil.questionTypeRecognition(question);
        if (questionType == NLPUtil.OUTLOOK_QUESTION) {
            int answerType = TYPE_DEFAULT_ANSWER;
            Long exhibitId = null;
            // 长什么样之类的问题 直接搜索数据库展品图片
            if (exhibitTexts.size() != 0) {
                String figureUrl = exhibitService.selectExhibitFigureUrlByLabel(label.get(0));
                if (StringUtils.hasText(figureUrl)) {
                    answer = figureUrl;
                    answerType = TYPE_IMAGE_ANSWER;
                }
                RecommendQuestion newQuestion = new RecommendQuestion();
                newQuestion.setQuestionText(question);
                newQuestion.setAnswerType(answerType);
                newQuestion.setAnswerText(answer);
                newQuestion.setExhibitId(exhibitTexts.get(0).getExhibitId());
                exhibitId = newQuestion.getExhibitId();
                newQuestion.setMuseumId(museumId);
                recommendQuestionService.save(newQuestion);
            } else {
                recommendQuestionService.insertIrrelevantQuestion(question, museumId);
            }
            // 写入缓存
            recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
            return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, answerType, null, exhibitId);
        }

        Long textId = null;

        List<RpcExhibitText> rpcTexts =
                exhibitTexts.stream()
                        .map(e -> RpcExhibitText.newBuilder()
                                .setText(e.getText())
                                .setId(e.getId())
                                .build())
                        .collect(Collectors.toList());

        log.debug("Question: {}, rpcTexts size: {}", question, rpcTexts.size());
        if (rpcTexts.size() != 0) {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(rpcTexts)
                    .setStatus(questionType)
                    .build();

            try {
                AnswerWithTextId answerWithTextId = myServiceBlockingStub
                        .sayHello(helloRequest)
                        .getAnswerWithTextId();
                String resp = answerWithTextId.getAnswer();
                log.debug("grpc response for question {}, {}", question, resp);
                // 替换grpc返回的所有包含[CLS]的占位符，如果仅包含占位符则返回"无法回答"
                resp = PLACEHOLDER_PATTERN.matcher(resp).replaceAll("");
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
            Long userLocation = exhibitService.selectExhibitionHallIdByExhibitId(exhibitTexts.get(0).getExhibitId());
            if (userLocation != null) {
                userService.setUserLocation(userId, userLocation);
            }
        }

        int answerType = getAnswerType(answer);
        // 将答案写入数据库中
        RecommendQuestion newQuestion = new RecommendQuestion();
        newQuestion.setQuestionText(question);
        newQuestion.setAnswerType(answerType);
        newQuestion.setAnswerText(answerType == TYPE_DEFAULT_ANSWER ? null : answer);
        newQuestion.setExhibitId(exhibitTexts.size() == 0 ? null : exhibitTexts.get(0).getExhibitId());
        newQuestion.setExhibitTextId(textId);
        newQuestion.setMuseumId(museumId);
        recommendQuestionService.save(newQuestion);

        // 写入缓存后，更新用户历史提问
        recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
        feedbackService.insertUserQuestion(userId, recommendQuestion.getId());

        return new AnswerWithTextIdDTO(recommendQuestion.getId(), answer, answerType, textId, newQuestion.getExhibitId());
    }
}
