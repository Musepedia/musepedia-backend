package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.ExhibitDTO;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.UserService;
import com.mimiter.mgs.core.service.mapstruct.ExhibitDTOMapper;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
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

    private final UserService userService;

    private final ExhibitDTOMapper exhibitDTOMapper;

    @ApiOperation("获取展品详细信息，用于问答")
    @AnonymousAccess
    @GetMapping("/info")
    public BaseResponse<ExhibitDTO> getExhibitInfo(@RequestParam Long id) {
        // no exhibition hall info
        Exhibit exhibit = exhibitService.getExhibitInfoById(id);

        // 根据问题更新用户所在位置
        System.out.println(exhibit.getHallId());
        userService.setUserLocation(SecurityUtil.getCurrentUserId(), exhibit.getHallId());

        ExhibitDTO dto = exhibitDTOMapper.toDto(exhibit);
        return BaseResponse.ok("ok", dto);
    }

    /**
     * 随机从每个展区中获取至多{@code limitPerExhibitionHall}个展品供问卷使用
     */
    @ApiOperation("对于每个展区的随机获取一件展品信息，用于问卷")
    @AnonymousAccess
    @GetMapping("/random")
    public BaseResponse<List<ExhibitDTO>> getRandomExhibitPreference() {

        List<Exhibit> exhibits = exhibitService.getRandomExhibits(4, ThreadContextHolder.getCurrentMuseumId());
        return BaseResponse.ok("ok", exhibitDTOMapper.toDto(exhibits));
    }
}
