package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cucumber.java.eo.Se;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    @Select("select exhibit_text from tbl_exhibit_text where exhibit_id in (select exhibit_id from tbl_exhibit where exhibit_label = #{label});")
    List<String> selectByLabel(@Param("label") String label);

    @Select("select exhibit_label from tbl_exhibit;")
    List<String> selectAllLabels();

    @Select("select exhibit_id from tbl_exhibit where exhibit_label = #{label};")
    Integer selectExhibitIdByLabel(@Param("label") String label);

    @Select("select exhibit_text from tbl_exhibit_text where exhibit_id = #{id}")
    List<String> selectExhibitTextByExhibitId(@Param("id") int id);
}
