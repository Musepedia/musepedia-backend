package com.mimiter.mgs.admin.mapstruct;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mimiter.mgs.admin.model.dto.UserDTO;
import com.mimiter.mgs.admin.model.entity.AdminUser;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseDTOMapper<UserDTO, AdminUser> {
}
