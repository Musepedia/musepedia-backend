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
            "(question_text, answer_type, answer_text, exhibit_id, exhibit_text_id) " +
            "values " +
            "(#{question_text}, #{answer_type}, #{answer_text}, #{id}, #{text_id}) ")
    void insertQuestion(@Param("question_text") String questionText, @Param("answer_type") int answerType,
                        @Param("answer_text") String answerText, @Param("id") Long exhibitId, @Param("text_id") Long textId);

    @ResultMap("mybatis-plus_RecommendQuestion")
    @Select("select * " +
            "from tbl_recommend_question " +
            "where question_text = #{text} ")
    RecommendQuestion selectQuestionByText(@Param("text") String question);

    @Select("select question_text from tbl_recommend_question " +
            "where exhibit_id = #{id} " +
            "order by rand() limit 1 ")
    RecommendQuestion selectRandomQuestionWithSameExhibitId(@Param("id") Long exhibitId);

    @Select("select t1.question_text, t1.answer_type, t1.answer_text, t2.exhibit_figure_url from tbl_recommend_question t1, tbl_exhibit t2 " +
            "where t1.exhibit_id = t2.exhibit_id and t1.exhibit_id = #{id} and t1.answer_type != 0 and t1.exhibit_id is not null " +
            "order by question_freq desc " +
            "limit #{count}")
    List<RecommendQuestion> selectMostFrequentQuestions(@Param("count") int count, @Param("id") Long exhibitId);
}
