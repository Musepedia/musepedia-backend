package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.PreferenceDTO;
import cn.abstractmgs.core.model.entity.Exhibit;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PreferenceDTOMapper extends BaseDTOMapper<PreferenceDTO, Exhibit> {
}
