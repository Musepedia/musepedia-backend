package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.admin.model.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleRepository extends BaseMapper<Role> {

    @Select("SELECT r.* FROM sys_role r " +
            "LEFT JOIN sys_user_role ur " +
            "   ON r.role_id = ur.role_id " +
            "WHERE ur.user_id=#{userId}")
    List<Role> findByUserId(@Param("userId") Long userId);
}
