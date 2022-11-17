package com.mimiter.mgs.core.service.mapstruct;

import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.core.model.dto.SimpleExhibitDTO;
import com.mimiter.mgs.model.entity.Exhibit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ExhibitionHallDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimpleExhibitDTOMapper extends BaseDTOMapper<SimpleExhibitDTO, Exhibit> {
}
