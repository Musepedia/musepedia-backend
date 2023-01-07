package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.Exhibit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExhibitRepository extends BaseMapper<Exhibit> {

    @ResultMap("mybatis-plus_Exhibit")
    @Select("SELECT * FROM tbl_exhibit WHERE exhibit_label=#{label}")
    Exhibit findByLabel(String label);
}
