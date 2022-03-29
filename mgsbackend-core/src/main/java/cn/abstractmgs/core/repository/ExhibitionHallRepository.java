package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitionHallRepository extends BaseMapper<ExhibitionHall> {

    @ResultMap("mybatis-plus_ExhibitionHall")
    @Select("select * from tbl_exhibition_hall")
    List<ExhibitionHall> selectAll();
}
