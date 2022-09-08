package cn.abstractmgs.core.repository;

import cn.abstractmgs.model.entity.QuestionFeedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

@Mapper
public interface QuestionFeedbackRepository extends BaseMapper<QuestionFeedback> {

    @Update("update tbl_user_question " +
            "set feedback = #{feedback} " +
            "where user_id = #{userId} and question_id = #{questionId}")
    Boolean updateUserFeedbackOnQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId, Boolean feedback);

    @Select("select user_id, question_id from tbl_user_question " +
            "where user_id = #{userId} and question_id = #{questionId}")
    QuestionFeedback selectUserQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId);
}
