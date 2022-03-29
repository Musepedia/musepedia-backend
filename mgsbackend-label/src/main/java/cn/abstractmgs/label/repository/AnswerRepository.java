package cn.abstractmgs.label.repository;

import cn.abstractmgs.label.model.entity.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface AnswerRepository extends BaseMapper<Answer> {

}
