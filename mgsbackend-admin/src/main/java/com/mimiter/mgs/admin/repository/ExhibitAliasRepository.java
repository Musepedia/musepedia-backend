package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExhibitAliasRepository extends BaseMapper<ExhibitAlias> {

    @Delete("DELETE FROM tbl_exhibit_alias WHERE exhibit_id=#{exhibitId}")
    boolean deleteByExhibitId(Long exhibitId);

    @ResultMap("mybatis-plus_ExhibitAlias")
    @Select("SELECT * FROM tbl_exhibit_alias WHERE exhibit_id = #{exhibitId}")
    ExhibitAlias findByExhibitId(Long exhibitId);
}
