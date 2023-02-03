package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Museum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMuseumRepository extends BaseMapper<Museum> {

    @ResultMap("mybatis-plus_Museum")
    @Select("SELECT * FROM tbl_museum WHERE museum_name=#{name}")
    Museum findByName(String name);
}
