package com.mimiter.mgs.core.service.mapstruct;

import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.core.model.dto.DataAnalysisExhibitionHallDTO;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用于数据分析的展区DTO Mapper
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataAnalysisExhibitionHallDTOMapper
        extends BaseDTOMapper<DataAnalysisExhibitionHallDTO, ExhibitionHall> {
}
