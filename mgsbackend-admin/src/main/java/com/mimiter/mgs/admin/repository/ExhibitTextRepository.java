package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitText;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    @Delete("DELETE FROM tbl_exhibit_text WHERE exhibit_id=#{exhibitId}")
    boolean deleteByExhibitId(Long exhibitId);

}
