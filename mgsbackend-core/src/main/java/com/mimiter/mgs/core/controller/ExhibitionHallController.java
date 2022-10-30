package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.core.repository.ExhibitionHallRepository;
import com.mimiter.mgs.core.service.mapstruct.ExhibitionHallDTOMapper;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exhibition-hall")
@RequiredArgsConstructor
public class ExhibitionHallController {

    private final ExhibitionHallRepository exhibitionHallRepository;

    private final ExhibitionHallDTOMapper exhibitionHallDTOMapper;

    @AnonymousAccess
    @GetMapping
    public BaseResponse<List<ExhibitionHallDTO>> listExhibitionHall(){
        return BaseResponse.ok("ok", exhibitionHallDTOMapper.toDto(exhibitionHallRepository.listByMuseumId(ThreadContextHolder.getCurrentMuseumId())));
    }
}
