package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cucumber.java.eo.Se;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    List<ExhibitText> selectByLabel(@Param("labels") List<String> labels);

    String selectExhibitTextByKeyword(@Param("keyword") String keyword);

    @Select("select exhibit_label " +
            "from tbl_exhibit " +
            "union " +
            "select exhibit_alias " +
            "from tbl_exhibit_alias")
    List<String> selectAllLabelsWithAliases();
}
