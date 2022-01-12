package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserPreferenceRepository extends BaseMapper<UserPreference> {

    @Delete("delete from tbl_user_preference where user_id = #{id}")
    void deleteUserPreferenceById(@Param("id") Long id);

    int insertUserPreferenceById(@Param("id") Long id, @Param("hallIds") List<Integer> hallIds);
}
