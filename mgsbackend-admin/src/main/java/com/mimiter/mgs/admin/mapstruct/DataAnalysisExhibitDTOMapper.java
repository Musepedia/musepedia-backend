package com.mimiter.mgs.admin.mapstruct;

import com.mimiter.mgs.admin.model.dto.DataAnalysisExhibitDTO;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.model.entity.Exhibit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * 用于数据分析的展品DTO Mapper
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DataAnalysisExhibitDTOMapper extends BaseDTOMapper<DataAnalysisExhibitDTO, Exhibit> {
}
