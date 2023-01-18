package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.Exhibit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitRepository extends BaseMapper<Exhibit> {

    @ResultMap("mybatis-plus_Exhibit")
    @Select("SELECT * FROM tbl_exhibit WHERE exhibit_label=#{label}")
    Exhibit findByLabel(String label);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select t2.*, sum(t1.question_freq) question_count " +
            "from tbl_recommend_question t1, tbl_exhibit t2 " +
            "where t1.museum_id = #{museumId} and t1.exhibit_id = t2.exhibit_id " +
            "group by t1.exhibit_id")
    List<Exhibit> getQuestionCountPerExhibit(@Param("museumId") Long museumId);

}
