package cn.abstractmgs.label.repository;

import cn.abstractmgs.label.model.entity.Paragraph;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Mapper
public interface ParagraphRepository extends BaseMapper<Paragraph> {

}
