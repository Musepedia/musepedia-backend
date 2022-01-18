package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.Exhibit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ExhibitRepository extends BaseMapper<Exhibit> {

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select exhibit_label, exhibit_description, exhibit_url from tbl_exhibit where exhibit_id = #{id}")
    Exhibit selectInfoById(@Param("id") Long id);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select * from " +
            "(" +
            "   select *, row_number() over(partition by t.exhibition_hall_id order by rand()) as row_num" +
            "   from tbl_exhibit t " +
            ") t2 " +
            "where t2.row_num = 1;")
    List<Exhibit> getRandomExhibits(@Param("limit") int limit);
}
