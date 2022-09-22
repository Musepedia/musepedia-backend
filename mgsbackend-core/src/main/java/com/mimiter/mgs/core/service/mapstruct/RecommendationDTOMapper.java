package com.mimiter.mgs.core.service.mapstruct;

import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.core.model.dto.RecommendationDTO;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RecommendationDTOMapper extends BaseDTOMapper<RecommendationDTO, RecommendQuestion> {

    @Override
    @Mapping(target = "questionId", source = "id")
    RecommendationDTO toDto(RecommendQuestion entity);
}
