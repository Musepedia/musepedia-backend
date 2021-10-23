package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.Text;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TextRepository extends BaseMapper<Text> {

    @Select("select exhibits_text from text where exhibits_label = #{label}")
    List<Text> selectByLabel(@Param("label") String label);
}
