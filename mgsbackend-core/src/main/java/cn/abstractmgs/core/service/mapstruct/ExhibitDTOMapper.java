package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.ExhibitDTO;
import cn.abstractmgs.model.entity.Exhibit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ExhibitionHallDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExhibitDTOMapper extends BaseDTOMapper<ExhibitDTO, Exhibit> {
}
