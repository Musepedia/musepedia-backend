package cn.abstractmgs.label.repository;

import cn.abstractmgs.label.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface QuestionRepository extends BaseMapper<Question> {

    @Select("SELECT * FROM question WHERE paragraph_id = #{id}")
    @ResultMap("questionMapper")
    List<Question> selectByParagraphId(Long id);
}
