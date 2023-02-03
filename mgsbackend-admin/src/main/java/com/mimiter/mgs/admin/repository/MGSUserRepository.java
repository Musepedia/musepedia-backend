package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface MGSUserRepository extends BaseMapper<User> {

    @Select("select count(distinct(user_id))\n" +
            "from tbl_user_question t1,\n" +
            "tbl_recommend_question t2\n" +
            "where user_id not in (\n" +
            "        select distinct t1.user_id\n" +
            "        from tbl_user_question t1,\n" +
            "             tbl_recommend_question t2\n" +
            "        where t1.question_id = t2.question_id\n" +
            "          and t2.exhibit_id is not null\n" +
            "          and t2.museum_id = #{museumId}\n" +
            "          and date_format(t1.create_time, '%Y-%m-%d') < #{date}\n" +
            ") and date_format(t1.create_time, '%Y-%m-%d') = #{date}\n" +
            "  and t1.question_id = t2.question_id\n" +
            "  and t2.exhibit_id is not null\n" +
            "  and t2.museum_id = #{museumId}")
    int getNewUserCount(@Param("museumId") Long museumId, @Param("date") LocalDate date);
}
