package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserPreferenceRepository extends BaseMapper<UserPreference> {

    @Delete("delete from tbl_user_preference where user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);

    @ResultMap("cn.abstractmgs.core.repository.ExhibitionHallRepository.mybatis-plus_ExhibitionHall")
    @Select("select * from tbl_exhibition_hall h " +
            "left join tbl_user_preference p " +
            "on p.exhibition_hall_id = h.exhibition_hall_id " +
            "where p.user_id = #{userId}")
    List<ExhibitionHall> getPreferredHallByUserId(@Param("userId") Long userId);

    int insertUserPreferenceById(@Param("userId") Long userId, @Param("hallIds") List<Long> hallIds);
}
