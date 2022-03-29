package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.Exhibit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.cucumber.java.eo.Se;
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

    @Select("select substring_index(group_concat(t.exhibit_id order by rand()), ',', #{limit}) " +
            "from tbl_exhibit t " +
            "group by t.exhibition_hall_id ")
    List<String> selectRandomExhibitId(@Param("limit") int limitPerExhibitionHall);

    List<Exhibit> selectRandomExhibits(@Param("ids") List<Integer> ids);

    @Select("select exhibit_figure_url from tbl_exhibit where exhibit_label = #{label}")
    String selectExhibitFigureUrlByLabel(@Param("label") String label);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select * " +
            "from tbl_exhibit " +
            "where exhibit_id != #{id} and exhibition_hall_id = ( " +
            "select exhibition_hall_id " +
            "from tbl_exhibit " +
            "where exhibit_id = #{id} " +
            ")")
    List<Exhibit> getExhibitsInSameExhibitionHall(@Param("id") Long id);

    @Select("select " +
            "(select exhibition_hall_id from tbl_exhibit where exhibit_id = #{id1}) " +
            "= " +
            "(select exhibition_hall_id from tbl_exhibit where exhibit_id = #{id2})")
    boolean isSameExhibitionHall(@Param("id1") int id1, @Param("id2") int id2);
}
