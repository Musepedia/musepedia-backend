package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.ExhibitionHallDTO;
import cn.abstractmgs.core.model.entity.ExhibitionHall;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExhibitionHallDTOMapper extends BaseDTOMapper<ExhibitionHallDTO, ExhibitionHall> {
}
