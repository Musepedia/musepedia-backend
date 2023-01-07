package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExhibitAliasRepository extends BaseMapper<ExhibitAlias> {

    @Delete("DELETE FROM tbl_exhibit_alias WHERE exhibit_id=#{exhibitId}")
    boolean deleteByExhibitId(Long exhibitId);
}
