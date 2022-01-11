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

    @Select("select substring_index(group_concat(t.exhibit_id order by rand()), ',', #{number}) " +
            "from tbl_exhibit t " +
            "group by t.exhibition_hall_id ")
    List<String> selectRandomExhibitId(@Param("number") int number);

    List<Exhibit> selectRandomExhibitPreference(List<Integer> ids);
}
