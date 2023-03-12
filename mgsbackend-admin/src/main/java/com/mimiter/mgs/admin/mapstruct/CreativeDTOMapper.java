package com.mimiter.mgs.admin.mapstruct;

import com.mimiter.mgs.admin.model.dto.CulturalCreativeDTO;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.model.entity.CulturalCreative;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreativeDTOMapper extends BaseDTOMapper<CulturalCreativeDTO, CulturalCreative> {
}
