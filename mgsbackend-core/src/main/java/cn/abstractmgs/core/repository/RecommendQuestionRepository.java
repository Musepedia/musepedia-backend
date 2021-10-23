package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendQuestionRepository extends BaseMapper<RecommendQuestion> {
//    @Select("select question_text from recommend_question where question_id = #{id}")
//    @Results(id = "recommendQuestionMap", value = {
//            @Result(property = "questionId", column = "question_id"),
//            @Result(property = "question", column = "question_text",many = @Many),
//    })
//    List<RecommendQuestion> selectByQuestionId(@Param("id") Long id);
}
