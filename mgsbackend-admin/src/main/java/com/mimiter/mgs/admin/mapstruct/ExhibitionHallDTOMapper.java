package com.mimiter.mgs.admin.mapstruct;

import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExhibitionHallDTOMapper extends BaseDTOMapper<ExhibitionHallDTO, ExhibitionHall> {
}
