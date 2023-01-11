package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionRepository extends BaseMapper<RecommendQuestion> {

    @ResultMap("mybatis-plus_RecommendQuestion")
    @Select("SELECT * FROM tbl_recommend_question WHERE exhibit_id = #{exhibitId}")
    RecommendQuestion findByExhibitId(Long exhibitId);
}
