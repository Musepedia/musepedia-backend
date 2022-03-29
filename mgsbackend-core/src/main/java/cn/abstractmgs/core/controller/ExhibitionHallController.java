package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.ExhibitionHallDTO;
import cn.abstractmgs.core.repository.ExhibitionHallRepository;
import cn.abstractmgs.core.service.mapstruct.ExhibitionHallDTOMapper;
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
    public BaseResponse<List<ExhibitionHallDTO>> listExhibitionHall(Long museumId){
        return BaseResponse.ok("ok", exhibitionHallDTOMapper.toDto(exhibitionHallRepository.selectAll()));
    }
}
