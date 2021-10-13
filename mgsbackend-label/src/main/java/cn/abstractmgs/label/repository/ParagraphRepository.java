package cn.abstractmgs.label.repository;

import cn.abstractmgs.label.model.entity.Paragraph;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface ParagraphRepository extends BaseMapper<Paragraph> {
}
