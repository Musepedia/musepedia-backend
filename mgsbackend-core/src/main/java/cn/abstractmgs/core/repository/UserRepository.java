package cn.abstractmgs.core.repository;

import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository extends BaseMapper<User> {

    @ResultMap("mybatis-plus_User")
    @Select("select t1.* from tbl_user t1 " +
            "left join tbl_user_wx_openid t2 " +
            "on t1.user_id = t2.user_id " +
            "where t2.wx_openid = #{openid}")
    User getByOpenid(@Param("openid") String openid);

    @Select("select * from tbl_user where phone_number = #{phoneNumber}")
    User getByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @ResultMap("mybatis-plus_User")
    @Select("select * from tbl_user where nickname = #{nickname}")
    List<User> listByNickname(@Param("nickname") String nickname);

}
