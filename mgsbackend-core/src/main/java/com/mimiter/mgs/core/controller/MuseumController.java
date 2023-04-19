package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.MuseumDTO;
import com.mimiter.mgs.core.service.MuseumService;
import com.mimiter.mgs.core.service.mapstruct.MuseumDTOMapper;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/museum")
@RequiredArgsConstructor
public class MuseumController {

    private final MuseumService museumService;

    private final MuseumDTOMapper museumDTOMapper;

    @AnonymousAccess
    @GetMapping
    public BaseResponse<List<MuseumDTO>> listMuseum() {
        return BaseResponse.ok("ok", museumDTOMapper.toDto(museumService.list()));
    }

    @AnonymousAccess
    @GetMapping("/current")
    public BaseResponse<MuseumDTO> getCurrentMuseum() {
        Long museumId = ThreadContextHolder.getCurrentMuseumId();
        return BaseResponse.ok("ok", museumDTOMapper.toDto(museumService.getById(museumId)));
    }
}
