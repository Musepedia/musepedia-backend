package com.mimiter.mgs.admin.mapstruct;

import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.model.entity.Exhibit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExhibitDTOMapper extends BaseDTOMapper<ExhibitDTO, Exhibit> {
}
