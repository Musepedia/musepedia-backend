package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitAliasRepository extends BaseMapper<ExhibitAlias> {
    @ResultMap("mybatis-plus_ExhibitAlias")
    @Select("Select * from tbl_exhibit_alias")
    List<ExhibitAlias> getAllAlias();
}