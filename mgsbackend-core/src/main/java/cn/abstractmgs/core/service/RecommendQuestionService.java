package cn.abstractmgs.core.service;

import cn.abstractmgs.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RecommendQuestionService extends IService<RecommendQuestion> {

    List<String> getRandomQuestions(int count, Long museumId);

    void updateQuestionFreqByText(String question, Long museumId);

    void insertQuestion(String questionText, int answerType, String answerText, Long exhibitId, Long exhibitTextId, Long museumId);

    void insertIrrelevantQuestion(String questionText, Long museumId);

    RecommendQuestion getRandomQuestionWithSameExhibitId(Long exhibitId);

    RecommendQuestion getRecommendQuestion(String question, Long museumId);

    RecommendQuestion selectQuestionByText(String question, Long museumId);

    List<RecommendQuestion> selectMostFrequentQuestions(int count, Long exhibitId);

    List<String> selectRecommendQuestions(String originalQuestion,String originalAnswer, Long museumId);

    List<RecommendQuestion> selectNonDislikeUserQuestion(Long userId, Long museumId);

    List<RecommendQuestion> selectQuestionsWithFreqAndLabels(Long museumId);

    List<RecommendQuestion> selectUserQuestionsWithLabels(Long museumId);
}
