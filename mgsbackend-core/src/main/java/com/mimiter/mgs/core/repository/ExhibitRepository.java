package com.mimiter.mgs.core.repository;

import com.mimiter.mgs.model.entity.Exhibit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExhibitRepository extends BaseMapper<Exhibit> {

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select exhibition_hall_id, exhibit_label, exhibit_description, exhibit_url " +
            "from tbl_exhibit " +
            "where exhibit_id = #{id}")
    Exhibit selectInfoById(@Param("id") Long id);

    // 随机选择每个展区下的limit个展品
    @Select("select substring_index(group_concat(t1.exhibit_id order by rand()), ',', #{limit}) " +
            "from tbl_exhibit t1 " +
            "where t1.exhibition_hall_id in ( " +
            "    select exhibition_hall_id from tbl_exhibition_hall t2 " +
            "    where t2.museum_id = #{id} and t2.is_enabled = true " +
            ") " +
            "group by t1.exhibition_hall_id ")
    List<String> selectRandomExhibitId(@Param("limit") int limitPerExhibitionHall, @Param("id") Long museumId);

    // 查询展品（包括展区信息）
    List<Exhibit> selectRandomExhibits(@Param("ids") List<Integer> ids);

    @Select("select exhibit_figure_url from tbl_exhibit where exhibit_label = #{label}")
    String selectExhibitFigureUrlByLabel(@Param("label") String label);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select distinct t1.exhibit_id, t1.exhibition_hall_id, t1.exhibit_label, t1.exhibit_is_hot " +
            "from tbl_exhibit t1 " +
            "join tbl_recommend_question t2 on t1.exhibit_id = t2.exhibit_id " +
            "where t1.exhibit_id != #{id} and exhibition_hall_id = ( " +
            "select exhibition_hall_id " +
            "from tbl_exhibit " +
            "where exhibit_id = #{id})")
    List<Exhibit> getExhibitsInSameExhibitionHall(@Param("id") Long id);


    @Select("select exhibition_hall_id from tbl_exhibit where exhibit_id = #{id}")
    Long selectExhibitionHallIdByExhibitId(@Param("id") Long id);

    @Select("select exhibit_id from tbl_exhibit where exhibit_label = #{label} and museum_id = #{museumId}")
    Long selectExhibitIdByLabelAndMuseumId(@Param("label") String label, @Param("museumId") Long museumId);

    @Select("select " +
            "(select exhibition_hall_id from tbl_exhibit where exhibit_id = #{id1}) " +
            "= " +
            "(select exhibition_hall_id from tbl_exhibit where exhibit_id = #{id2})")
    boolean isSameExhibitionHall(@Param("id1") int id1, @Param("id2") int id2);


    @Select("select exhibit_id from tbl_exhibit " +
            "where exhibition_hall_id = ( " +
            "    select exhibition_hall_id from tbl_exhibit " +
            "    where exhibit_id = #{id} " +
            ") " +
            "order by exhibit_id")
    List<Long> selectExhibitIdsInSameExhibitionHall(@Param("id") Long id);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select * from tbl_exhibit " +
            "where exhibit_id = ( " +
            "    select exhibit_prev_id from tbl_exhibit " +
            "    where exhibit_id = #{id} " +
            ") or exhibit_id = ( " +
            "    select exhibit_next_id from tbl_exhibit " +
            "    where exhibit_id = #{id} " +
            ")")
    List<Exhibit> selectPreviousAndNextExhibitById(@Param("id") Long id);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select t2.exhibit_id, t2.exhibit_label, t2.exhibit_figure_url, t2.exhibit_description " +
            "from tbl_recommend_question t1, tbl_exhibit t2 " +
            "where t1.exhibit_id = t2.exhibit_id " +
            "   and t1.answer_type != 0 " +
            "   and t1.exhibit_id is not null " +
            "   and t1.museum_id = #{museumId} " +
            "   and t2.enabled = true" +
            "group by t1.exhibit_id " +
            "order by sum(t1.question_freq) desc " +
            "limit #{count}")
    List<Exhibit> selectMostFrequentExhibits(@Param("count") int count, @Param("museumId") Long museumId);

    @ResultMap("mybatis-plus_Exhibit")
    @Select("select t2.*, sum(t1.question_freq) question_count from tbl_recommend_question t1, tbl_exhibit t2\n" +
            "where t1.museum_id = #{museumId} and t1.exhibit_id = t2.exhibit_id\n" +
            "group by t1.exhibit_id")
    List<Exhibit> getQuestionCountPerExhibit(@Param("museumId") Long museumId);
}
