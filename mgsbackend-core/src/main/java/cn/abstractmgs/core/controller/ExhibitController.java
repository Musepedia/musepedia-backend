package cn.abstractmgs.core.controller;

import cn.abstractmgs.common.model.BaseResponse;
import cn.abstractmgs.core.model.dto.ExhibitDTO;
import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.service.ExhibitService;
import cn.abstractmgs.core.service.mapstruct.ExhibitDTOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/exhibit")
public class ExhibitController {

    @Autowired
    private ExhibitService exhibitService;

    @Autowired
    private ExhibitDTOMapper exhibitDTOMapper;

    @GetMapping
    public BaseResponse<ExhibitDTO> getExhibitInfo(@RequestParam Long id) {
        Exhibit exhibit = exhibitService.selectInfoById(id);
        ExhibitDTO dto = exhibitDTOMapper.toDto(exhibit);
        return BaseResponse.ok("ok", dto);
    }
}
