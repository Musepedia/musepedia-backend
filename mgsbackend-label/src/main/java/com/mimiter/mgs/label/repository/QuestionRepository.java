package com.mimiter.mgs.label.repository;

import com.mimiter.mgs.label.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionRepository extends BaseMapper<Question> {

    @Select("SELECT * FROM question WHERE paragraph_id = #{id}")
    @ResultMap("questionMapper")
    List<Question> selectByParagraphId(Long id);
}
