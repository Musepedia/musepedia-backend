package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendQuestionRepository extends BaseMapper<RecommendQuestion> {

    @Update("update tbl_recommend_question " +
            "set question_freq = question_freq + 1 " +
            "where question_text = #{text}")
    void updateQuestionFreqByText(@Param("text") String question);

    @Insert("insert into tbl_recommend_question " +
            "(question_text, answer_type, answer_text, exhibit_id) " +
            "values " +
            "(#{question_text}, #{answer_type}, #{answer_text}, #{id}) ")
    void insertQuestion(@Param("question_text") String questionText, @Param("answer_type") int answerType,
                        @Param("answer_text") String answerText, @Param("id") Long exhibitId);

    @Select("select question_text, answer_type, answer_text, exhibit_id " +
            "from tbl_recommend_question " +
            "where question_text = #{text} ")
    RecommendQuestion selectQuestionByText(@Param("text") String question);

    @Select("select question_text from tbl_recommend_question " +
            "where exhibit_id = #{id} " +
            "order by rand() limit 1 ")
    RecommendQuestion selectRandomQuestionWithSameExhibitId(@Param("id") Long exhibitId);
}
