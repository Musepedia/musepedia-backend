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
    @Select("select m.* " +
            "from tbl_museum m")
    List<Museum> selectAllMuseums();

    @ResultMap("mybatis-plus_Museum")
    @Select("select m.* " +
            "from tbl_museum m " +
            "where museum_is_service = true")
    List<Museum> selectAllServicedMuseums();

}
