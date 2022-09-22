package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.Museum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MuseumRepository extends BaseMapper<Museum> {

    @ResultMap("mybatis-plus_Museum")
    @Select("select museum_id, museum_name, museum_description, museum_logo_url, museum_is_service " +
            "from tbl_museum")
    List<Museum> selectAllMuseums();

    @ResultMap("mybatis-plus_Museum")
    @Select("select museum_id, museum_name, museum_description, museum_logo_url, museum_is_service " +
            "from tbl_museum " +
            "where museum_is_service = true")
    List<Museum> selectAllServicedMuseums();

}
