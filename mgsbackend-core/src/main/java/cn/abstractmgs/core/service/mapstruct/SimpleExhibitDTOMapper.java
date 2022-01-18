package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.SimpleExhibitDTO;
import cn.abstractmgs.core.model.entity.Exhibit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ExhibitionHallDTOMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SimpleExhibitDTOMapper extends BaseDTOMapper<SimpleExhibitDTO, Exhibit> {
}
