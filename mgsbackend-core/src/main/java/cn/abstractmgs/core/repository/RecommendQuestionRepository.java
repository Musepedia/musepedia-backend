package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RecommendQuestionRepository extends BaseMapper<RecommendQuestion> {

//    @Results(id = "recommendQuestionMapper", value = {
//            @Result(property = "id", column = "question_id"),
//            @Result(property = "question", column = "question_text"),
//    })
//    void resultMap();
}
