package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.RecommendationDTO;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
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
