package com.mimiter.mgs.core.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.OpenQAQuestion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpenQAQuestionRepository extends BaseMapper<OpenQAQuestion> {
}
