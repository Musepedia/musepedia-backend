package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository extends BaseMapper<User> {

    @ResultMap("mybatis-plus_User")
    @Select("select t1.* from tbl_user t1 " +
            "left join tbl_user_wx_openid t2 " +
            "on t1.user_id = t2.user_id " +
            "where t2.wx_openid = #{openid}")
    User getByOpenid(@Param("openid") String openid);

    @ResultMap("mybatis-plus_User")
    @Select("select * from tbl_user where nickname = #{nickname}")
    List<User> listByNickname(@Param("nickname") String nickname);

    @Insert("insert into tbl_user (nickname, avatar_url, phone_number, gender, age) " +
            "values " +
            "(#{nickname}, #{url}, #{phone}, #{gender}, #{age})")
    void insertUserSetting(@Param("nickname") String nickname,
                           @Param("url") String avatarUrl,
                           @Param("phone") String phoneNumber,
                           @Param("gender") GenderEnum gender,
                           @Param("age") AgeEnum age);

    @Insert("insert into tbl_user_question (user_id, question_id) " +
            "values " +
            "(#{userId}, #{questionId})")
    void insertUserQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId);

    @Update("update tbl_user_question " +
            "set feedback = #{feedback} " +
            "where user_id = #{userId} and question_id = #{questionId}")
    Boolean updateUserFeedbackOnQuestion(@Param("userId") Long userId, @Param("questionId") Long questionId, Boolean feedback);
}
