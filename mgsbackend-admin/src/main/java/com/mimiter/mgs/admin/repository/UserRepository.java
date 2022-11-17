package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserRepository extends BaseMapper<AdminUser> {

    @ResultMap("mybatis-plus_User")
    @Update("UPDATE sys_user SET password = #{password} WHERE user_id = #{userId}")
    void setPassword(Long userId, String password);

    /**
     * 查询重复手机号
     * 忽略 is_deleted=0
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE phone = #{phone} AND user_id != #{userId}")
    long countByPhone(Long userId, String phone);

    /**
     * 查询重复邮箱
     * 忽略 is_deleted=0
     */
    @Select("SELECT COUNT(*) FROM sys_user WHERE email = #{email} AND user_id != #{userId}")
    long countByEmail(Long userId, String email);

    @Select("SELECT * FROM sys_user WHERE username=#{username}")
    AdminUser findByUsername(String username);

    @Select("SELECT * FROM sys_user wHERE phone=#{phone}")
    AdminUser findByPhone(String phone);
}
