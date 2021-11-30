package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExhibitTextRepository extends BaseMapper<ExhibitText> {

    @Select("select exhibits_text from text where exhibits_label = #{label};")
    List<String> selectByLabel(@Param("label") String label);

    @Select("select exhibits_label from text;")
    List<String> selectAllLabels();
}
