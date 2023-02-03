package com.mimiter.mgs.admin.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.admin.model.entity.InstitutionAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InstitutionAdminRepository extends BaseMapper<InstitutionAdmin> {

    @ResultMap("mybatis-plus_InstitutionAdmin")
    @Select("SELECT * FROM sys_institution_admin WHERE user_id=#{id}")
    InstitutionAdmin findById(Long id);
}
