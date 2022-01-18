package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.annotation.AnonymousAccess;
import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.ExhibitDTO;
import cn.abstractmgs.core.model.dto.SimpleExhibitDTO;
import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.repository.ExhibitionHallRepository;
import cn.abstractmgs.core.service.ExhibitService;
import cn.abstractmgs.core.service.mapstruct.ExhibitDTOMapper;
import cn.abstractmgs.core.service.mapstruct.SimpleExhibitDTOMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/exhibit")
@RequiredArgsConstructor
public class ExhibitController {

    private final ExhibitService exhibitService;

    private final ExhibitionHallRepository exhibitionHallRepository;

    private final ExhibitDTOMapper exhibitDTOMapper;

    private final SimpleExhibitDTOMapper simpleExhibitDTOMapper;

    @ApiOperation("获取展品详细信息，用于问答")
    @AnonymousAccess
    @GetMapping("/info")
    public BaseResponse<ExhibitDTO> getExhibitInfo(@RequestParam Long id) {
        // no exhibition hall info
        Exhibit exhibit = exhibitService.getExhibitInfoById(id);
        ExhibitDTO dto = exhibitDTOMapper.toDto(exhibit);
        return BaseResponse.ok("ok", dto);
    }

    @ApiOperation("对于每个展区的随机获取一件展品信息，用于问卷")
    @AnonymousAccess
    @GetMapping("/random")
    public BaseResponse<List<SimpleExhibitDTO>> getRandomExhibitPreference() {
        List<Exhibit> exhibits = exhibitService.getRandomExhibits(16);
        for (Exhibit exhibit : exhibits) {
            exhibit.setExhibitionHall(exhibitionHallRepository.selectById(exhibit.getHallId()));
        }
        return BaseResponse.ok("ok", simpleExhibitDTOMapper.toDto(exhibits));
    }
}
