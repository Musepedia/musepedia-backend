package cn.abstractmgs.repository;

import cn.abstractmgs.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendQuestionRepository extends BaseMapper<RecommendQuestion> {
    @Select("select question_text from tblRSQuestion where question_id = #{id}")
    @Results(id = "recommendQuestionMap", value = {
            @Result(property = "questionId", column = "question_id"),
            @Result(property = "question", column = "question_text")
    })
    List<RecommendQuestion> selectByQuestionId(@Param("id") int id);

    @Select("select count(*) from tblRSQuestion")
    int countTable();
}
