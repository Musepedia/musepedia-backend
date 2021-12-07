package cn.abstractmgs.core.service.mapstruct;

import cn.abstractmgs.common.mapstruct.BaseDTOMapper;
import cn.abstractmgs.core.model.dto.UserDTO;
import cn.abstractmgs.core.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDTOMapper extends BaseDTOMapper<UserDTO, User> {
}
