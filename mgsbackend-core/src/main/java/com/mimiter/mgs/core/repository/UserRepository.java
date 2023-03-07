package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository extends BaseMapper<User> {

    @ResultMap("mybatis-plus_User")
    @Select("select t1.* from tbl_user t1 " +
            "left join tbl_user_wx_openid t2 " +
            "on t1.user_id = t2.user_id " +
            "where t2.wx_unionid = #{unionid}")
    User getByUnionid(@Param("unionid") String unionid);

    @Select("select * from tbl_user where phone_number = #{phoneNumber}")
    User getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @ResultMap("mybatis-plus_User")
    @Select("select * from tbl_user where nickname = #{nickname}")
    List<User> listByNickname(@Param("nickname") String nickname);

    @Select("select t1.age, t4.exhibit_label" +
            "from tbl_user t1, tbl_user_question t2, tbl_recommend_question t3, tbl_exhibit t4" +
            "where t1.user_id = t2.user_id, t2.question_id = t3.question_id, t3.exhibit_id = t4.exhibit_id and" +
            "t4.exhibition_hall_id in (select exhibition_hall_id from tbl_exhibition_hall" +
            "where museum_id = #{museumId})")
    List<User> ageWithLabels(@Param("museumId") Long museumId);

}
