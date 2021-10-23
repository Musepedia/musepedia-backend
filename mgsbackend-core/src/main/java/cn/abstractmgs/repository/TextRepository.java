package cn.abstractmgs.repository;

import cn.abstractmgs.model.entity.Text;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface TextRepository extends BaseMapper<Text> {
    @Select("select exhibits_text from tblDemo where exhibits_label = #{label}")
    @Results(id = "textMap", value = {
            @Result(property = "label", column = "exhibits_label"),
            @Result(property = "text", column = "exhibits_text")
    })
    List<Text> selectByLabel(@Param("label") String label);
}
