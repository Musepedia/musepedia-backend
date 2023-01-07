package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExhibitionHallRepository extends BaseMapper<ExhibitionHall> {

    @ResultMap("mybatis-plus_ExhibitionHall")
    @Select("SELECT * FROM tbl_exhibition_hall WHERE exhibition_hall_name=#{name}")
    ExhibitionHall findByName(String name);
}
