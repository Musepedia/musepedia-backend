package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.GPTCompletion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GPTCompletionRepository extends BaseMapper<GPTCompletion> {
}
