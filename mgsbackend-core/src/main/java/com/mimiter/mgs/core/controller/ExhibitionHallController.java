package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.core.repository.ExhibitionHallRepository;
import com.mimiter.mgs.core.service.mapstruct.ExhibitionHallDTOMapper;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("获取当前博物馆所有展区信息")
    @AnonymousAccess
    @GetMapping
    public BaseResponse<List<ExhibitionHallDTO>> listExhibitionHall() {
        // TODO hall
        Long currentMuseumId = ThreadContextHolder.getCurrentMuseumId();
        List<ExhibitionHall> halls = exhibitionHallRepository.listByMuseumId(currentMuseumId);
        return BaseResponse.ok("ok", exhibitionHallDTOMapper.toDto(halls));
    }
}
