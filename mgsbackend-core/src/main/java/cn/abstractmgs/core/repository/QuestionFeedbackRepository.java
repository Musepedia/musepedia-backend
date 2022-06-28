package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.QuestionFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface QuestionFeedbackRepository extends BaseMapper<QuestionFeedback> {

    @Update("update tbl_user_question " +
            "set feedback = #{feedback} " +
            "where user_id = #{userId} and question_id = #{questionId}")
    Boolean updateUserFeedbackOnQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId, Boolean feedback);
}
