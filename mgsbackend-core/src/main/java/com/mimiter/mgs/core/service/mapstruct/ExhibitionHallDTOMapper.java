package com.mimiter.mgs.core.service.mapstruct;

import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.core.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExhibitionHallDTOMapper extends BaseDTOMapper<ExhibitionHallDTO, ExhibitionHall> {
}
