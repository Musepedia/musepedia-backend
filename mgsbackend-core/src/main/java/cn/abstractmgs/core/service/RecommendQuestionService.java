package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecommendQuestionService extends IService<RecommendQuestion> {

    List<String> getRandomQuestions(int count);

    void updateQuestionFreqByText(@Param("text") String question);

    void insertQuestion(@Param("question_text") String questionText,
                        @Param("answer_type") int answerType,
                        @Param("answer_text") String answerText,
                        @Param("id") Long exhibitId,
                        Long exhibitTextId);

    void insertIrrelevantQuestion(@Param("question_text") String questionText);

    RecommendQuestion getRandomQuestionWithSameExhibitId(@Param("id") Long exhibitId);

    RecommendQuestion getRecommendQuestion(@Param("question") String question);

    RecommendQuestion selectQuestionByText(@Param("text") String question);

    List<RecommendQuestion> selectMostFrequentQuestions(@Param("count") int count);

    List<String> selectRecommendQuestions(String originalQuestion,String originalAnswer, Long museumId);

    List<RecommendQuestion> selectQuestionsWithFreqAndLabels(Long museumId);

    List<RecommendQuestion> selectUserQuestionsWithLabels(Long museumId);
}
