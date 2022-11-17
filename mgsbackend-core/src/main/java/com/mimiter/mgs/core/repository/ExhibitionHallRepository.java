package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitionHallRepository extends BaseMapper<ExhibitionHall> {

    @ResultMap("mybatis-plus_ExhibitionHall")
    @Select("select * from tbl_exhibition_hall where museum_id = #{id}")
    List<ExhibitionHall> listByMuseumId(@Param("id") Long id);

    @Select("select exhibition_hall_id from tbl_exhibition_hall where museum_id = #{id}")
    List<Integer> selectExhibitionHallIds(@Param("id") Long id);
}
