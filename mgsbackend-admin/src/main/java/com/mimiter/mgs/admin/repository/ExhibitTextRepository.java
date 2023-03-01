package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    @Delete("DELETE FROM tbl_exhibit_text WHERE exhibit_id=#{exhibitId}")
    boolean deleteByExhibitId(Long exhibitId);

}
