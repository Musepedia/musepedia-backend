package com.mimiter.mgs.admin.mapstruct;

import com.mimiter.mgs.admin.model.dto.QuestionDTO;
import com.mimiter.mgs.common.mapstruct.BaseDTOMapper;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionDTOMapper extends BaseDTOMapper<QuestionDTO, RecommendQuestion> {

}
