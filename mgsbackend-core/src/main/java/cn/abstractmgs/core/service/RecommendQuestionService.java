package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecommendQuestionService extends IService<RecommendQuestion> {

    List<String> getRandomQuestions(int count);

    void updateQuestionFreqByText(String question);

    void insertQuestion(String questionText, int answerType, String answerText, Long exhibitId, Long exhibitTextId);

    void insertIrrelevantQuestion(String questionText);

    RecommendQuestion getRandomQuestionWithSameExhibitId(Long exhibitId);

    RecommendQuestion getRecommendQuestion(String question);

    RecommendQuestion selectQuestionByText(String question);

    List<RecommendQuestion> selectMostFrequentQuestions(int count, Long exhibitId);

    List<String> selectRecommendQuestions(String originalQuestion,String originalAnswer, Long museumId);
}
