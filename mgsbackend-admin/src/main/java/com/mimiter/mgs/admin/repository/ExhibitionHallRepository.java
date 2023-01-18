package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitionHallRepository extends BaseMapper<ExhibitionHall> {

    @ResultMap("mybatis-plus_ExhibitionHall")
    @Select("select t2.*, count(t1.exhibit_id) exhibit_count from tbl_exhibit t1, tbl_exhibition_hall t2\n" +
            "where t1.museum_id = #{museumId} and t1.exhibition_hall_id = t2.exhibition_hall_id\n" +
            "group by t1.exhibition_hall_id")
    List<ExhibitionHall> getExhibitCountPerExhibitionHall(@Param("museumId") Long museumId);


    @ResultMap("mybatis-plus_ExhibitionHall")
    @Select("select t2.exhibition_hall_id, sum(t1.question_freq) question_count " +
            "from tbl_recommend_question t1, tbl_exhibit t2\n" +
            "where t1.museum_id = #{museumId} and t1.exhibit_id = t2.exhibit_id\n" +
            "group by t2.exhibition_hall_id")
    List<ExhibitionHall> getQuestionCountPerExhibitionHall(@Param("museumId") Long museumId);
}
