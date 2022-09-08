package cn.abstractmgs.core.repository;

import cn.abstractmgs.model.entity.ExhibitText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
