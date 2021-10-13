package cn.abstractmgs.label.repository;

import cn.abstractmgs.label.model.entity.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface QuestionRepository extends BaseMapper<Question> {

    @Select("SELECT question_id, text, paragraph_id FROM question WHERE paragraph_id = #{id}")
    @ResultMap("questionMapper")
    List<Question> selectByParagraphId(Long id);
}
