package cn.abstractmgs.core.repository;

import cn.abstractmgs.model.entity.ExhibitionHall;
import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPreferenceRepository extends BaseMapper<UserPreference> {

    @Delete("delete from tbl_user_preference t1 " +
            "where t1.user_id = #{userId} and t1.exhibition_hall_id in ( " +
            "    select t2.exhibition_hall_id from tbl_exhibition_hall t2 " +
            "    where t2.museum_id = #{museumId} " +
            ")")
    void deleteByUserId(@Param("userId") Long userId, @Param("museumId") Long museumId);

    @ResultMap("cn.abstractmgs.core.repository.ExhibitionHallRepository.mybatis-plus_ExhibitionHall")
    @Select("select * from tbl_exhibition_hall h " +
            "left join tbl_user_preference p " +
            "on p.exhibition_hall_id = h.exhibition_hall_id " +
            "where p.user_id = #{userId} and h.museum_id = #{museumId}")
    List<ExhibitionHall> getPreferredHallByUserId(@Param("userId") Long userId, @Param("museumId") Long museumId);

    int insertUserPreferenceById(@Param("userId") Long userId, @Param("hallIds") List<Long> hallIds);
}
