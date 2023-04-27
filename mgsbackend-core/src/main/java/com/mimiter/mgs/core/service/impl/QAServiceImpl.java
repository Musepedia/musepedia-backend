package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.core.MyServiceGrpc;
import com.mimiter.mgs.core.QAReply;
import com.mimiter.mgs.core.QARequest;
import com.mimiter.mgs.core.RpcExhibitText;
import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;
import com.mimiter.mgs.core.repository.OpenQAQuestionRepository;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.model.entity.OpenQAQuestion;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.service.*;
import com.mimiter.mgs.core.utils.NLPUtil;
import com.mimiter.mgs.core.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mimiter.mgs.model.entity.Museum.*;

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

    private final MuseumService museumService;

    private final OpenQAQuestionRepository openQAQuestionRepository;

    private  final LuceneSearchService luceneSearchService;
    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub qaRpcService;

    public static final Pattern URL_PATTERN = Pattern.compile(
            "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(.*)");

    private static final String DEFAULT_ANSWER_TEXT = "暂时无法回答这个问题";

    public static final int TYPE_DEFAULT_ANSWER = 0;

    public static final int TYPE_TEXT_ANSWER = 1;

    public static final int TYPE_IMAGE_ANSWER = 2;

    public static final int QA_TYPE_DEFAULT = 0;
    public static final int QA_TYPE_OPEN_QA = 1;
    public static final int QA_TYPE_GPT = 2;

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
    public AnswerWithTextIdDTO getAnswer(String question, Long museumId) throws IOException, ParseException {
        // 尝试从缓存中和数据库中获取答案
        RecommendQuestion recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
        int qaType = QA_TYPE_DEFAULT;

        Long userId = SecurityUtil.getCurrentUserId();
        Museum museum = museumService.getById(museumId);
        boolean hasOpenQAPermission = museum.hasPermission(PERMISSION_OPEN_QA);
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

            // 如果已有相同问题且能够回答，或者博物馆无OPENQA权限，直接返回
            if (recommendQuestion.getAnswerType() != 0 || !hasOpenQAPermission) {
                return new AnswerWithTextIdDTO(
                        recommendQuestion.getId(), recommendQuestion.getAnswerText(),
                        recommendQuestion.getAnswerType(), recommendQuestion.getExhibitTextId(),
                        recommendQuestion.getExhibitId(), qaType, null);
            }
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
            return new AnswerWithTextIdDTO(
                    recommendQuestion.getId(), answer, TYPE_DEFAULT_ANSWER,
                    null, null, qaType, null);
        }

        List<ExhibitText> exhibitTexts = luceneSearchService.getTopKTexts(question, museumId, 3);
        Long exhibitId = exhibitTexts.size() == 0 ? null : exhibitTexts.get(0).getExhibitId();
        int questionType = nlpUtil.questionTypeRecognition(question);
        if (questionType == NLPUtil.OUTLOOK_QUESTION) {
            int answerType = TYPE_DEFAULT_ANSWER;
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
                newQuestion.setExhibitId(exhibitId);
                newQuestion.setMuseumId(museumId);
                recommendQuestionService.save(newQuestion);
            } else {
                recommendQuestionService.insertIrrelevantQuestion(question, museumId);
            }
            // 写入缓存
            recommendQuestion = recommendQuestionService.getRecommendQuestion(question, museumId);
            return new AnswerWithTextIdDTO(
                    recommendQuestion.getId(), answer, answerType,
                    null, exhibitId, qaType, exhibitTexts);
        }

        // No QA & No OpenQA
        boolean hasQAPermission = museum.hasPermission(PERMISSION_QA);
        if (!hasOpenQAPermission && !hasQAPermission) {
            return new AnswerWithTextIdDTO(
                    null, answer, TYPE_DEFAULT_ANSWER,
                    null, exhibitId, qaType, exhibitTexts);
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
            QARequest qaRequest = QARequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(rpcTexts)
                    .setStatus(questionType)
                    .build();

            try {
                // 根据博物馆是否有OpenQA权限决定
                QAReply qaReply = hasOpenQAPermission
                        ? qaRpcService.getAnswerWithOpenQA(qaRequest)
                        : qaRpcService.getAnswer(qaRequest);
                qaType = qaReply.getFromOpenQa() ? QA_TYPE_OPEN_QA : QA_TYPE_DEFAULT;
                String resp = qaReply.getAnswer();
                log.debug("grpc response for question {}, {}", question, resp);
                if (resp.length() != 0) {
                    // has exact answer
                    answer = resp;
                    textId = qaReply.getTextId();
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
        Long questionId = null;
        // 将答案写入数据库中
        if (qaType == QA_TYPE_OPEN_QA) {
            OpenQAQuestion qaQuestion = new OpenQAQuestion();
            qaQuestion.setAnswer(answerType == TYPE_DEFAULT_ANSWER ? null : answer);
            qaQuestion.setQuestion(question);
            qaQuestion.setOpenDocumentId(textId);
            qaQuestion.setUserId(userId);
            qaQuestion.setMuseumId(museumId);
            openQAQuestionRepository.insert(qaQuestion);
            questionId = qaQuestion.getId();
        } else {
            RecommendQuestion newQuestion = new RecommendQuestion();
            newQuestion.setQuestionText(question);
            newQuestion.setAnswerType(answerType);
            newQuestion.setAnswerText(answerType == TYPE_DEFAULT_ANSWER ? null : answer);
            newQuestion.setExhibitId(exhibitId);
            newQuestion.setExhibitTextId(textId);
            newQuestion.setMuseumId(museumId);
            recommendQuestionService.save(newQuestion);
            questionId = newQuestion.getId();
            // 写入缓存后，更新用户历史提问
            feedbackService.insertUserQuestion(userId, questionId);
        }

        return new AnswerWithTextIdDTO(
                questionId, answer, answerType, textId, exhibitId, qaType, exhibitTexts);
    }
}
