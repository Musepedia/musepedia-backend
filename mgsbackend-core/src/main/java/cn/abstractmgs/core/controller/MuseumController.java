package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.MuseumDTO;
import cn.abstractmgs.core.service.MuseumService;
import cn.abstractmgs.core.service.mapstruct.MuseumDTOMapper;
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
        return BaseResponse.ok("ok", museumDTOMapper.toDto(museumService.selectAllMuseums()));
    }

    @AnonymousAccess
    @GetMapping("/current")
    public BaseResponse<MuseumDTO> getCurrentMuseum() {
        return BaseResponse.ok("ok", museumDTOMapper.toDto(museumService.selectCurrentMuseum()));
    }
}
