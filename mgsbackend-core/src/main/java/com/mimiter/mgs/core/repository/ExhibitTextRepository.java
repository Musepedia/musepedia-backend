package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.ExhibitText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cucumber.java.eo.Se;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    List<ExhibitText> selectByLabel(@Param("labels") List<String> labels, @Param("id") Long museumId);

    String selectExhibitTextByKeyword(@Param("keyword") String keyword);

    @Select("select exhibit_label " +
            "from tbl_exhibit " +
            "where exhibition_hall_id in ( " +
            "    select exhibition_hall_id from tbl_exhibition_hall " +
            "    where museum_id = #{id} " +
            ") " +
            "union " +
            "select exhibit_alias " +
            "from tbl_exhibit_alias " +
            "where exhibit_id in ( " +
            "    select exhibit_id from tbl_exhibit " +
            "    where exhibition_hall_id in ( " +
            "        select exhibition_hall_id from tbl_exhibition_hall " +
            "        where museum_id = #{id} " +
            "    ) " +
            ")")
    List<String> selectAllLabelsWithAliases(@Param("id") Long museumId);

    // 获取所有text，为创建lucene索引
    @ResultMap("mybatis-plus_ExhibitText")
    @Select("select exhibit_id, exhibit_text_id, exhibit_text from tbl_exhibit_text")
    List<ExhibitText> getAllTexts();

    // 根据exhibitId获取该展品所有的text
    @ResultMap(("mybatis-plus_ExhibitText"))
    @Select("select exhibit_text_id, exhibit_text from tbl_exhibit_text where exhibit_id = #{exhibitId}")
    List<ExhibitText> getTextsByExhibitId(@Param("exhibitId") Long exhibitId);
}
