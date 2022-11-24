package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminUserRepository extends BaseMapper<AdminUser> {

    @ResultMap("mybatis-plus_AdminUser")
    @Update("UPDATE sys_user SET password = #{password} WHERE user_id = #{userId}")
    void setPassword(Long userId, String password);

    @ResultMap("mybatis-plus_AdminUser")
    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    AdminUser findByUsername(String username);

    @ResultMap("mybatis-plus_AdminUser")
    @Update("UPDATE sys_user SET is_enabled = !is_enabled WHERE user_id = #{userId}")
    void toggleEnable(Long userId);
}
