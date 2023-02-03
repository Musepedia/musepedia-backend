package com.mimiter.mgs.admin.controller;

import com.mimiter.mgs.admin.mapstruct.DataAnalysisExhibitDTOMapper;
import com.mimiter.mgs.admin.mapstruct.DataAnalysisExhibitionHallDTOMapper;
import com.mimiter.mgs.admin.model.dto.DataAnalysisExhibitDTO;
import com.mimiter.mgs.admin.model.dto.DataAnalysisExhibitionHallDTO;
import com.mimiter.mgs.admin.model.dto.DataAnalysisUserCountDTO;
import com.mimiter.mgs.admin.model.request.GetNewUserCountReq;
import com.mimiter.mgs.admin.model.request.GetTopKTrendingReq;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.service.MGSUserService;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mimiter.mgs.admin.service.RoleService.STR_MUSEUM_ADMIN;
import static com.mimiter.mgs.admin.service.RoleService.STR_SYS_ADMIN;

@Slf4j
@RestController
@RequestMapping("/api/data-analysis")
@RequiredArgsConstructor
@Api(value = "数据分析API", tags = "数据分析API")
@PreAuthorize("@pm.check('" + STR_MUSEUM_ADMIN + "','" + STR_SYS_ADMIN + "')")
public class DataAnalysisController {

    private final MGSUserService userService;

    private final ExhibitService exhibitService;

    private final ExhibitionHallService exhibitionHallService;

    private final DataAnalysisExhibitDTOMapper exhibitDTOMapper;

    private final DataAnalysisExhibitionHallDTOMapper exhibitionHallDTOMapper;

    @ApiOperation("获取一段时间的用户新增数量")
    @GetMapping("/new-user")
    public BaseResponse<List<DataAnalysisUserCountDTO>> getNewUserCount(
            @ApiParam @Validated GetNewUserCountReq req) {
        LocalDate beginDate = req.getBeginDate();
        LocalDate endDate = req.getEndDate();
        Long museumId = req.getMuseumId();

        List<DataAnalysisUserCountDTO> userCounts = new ArrayList<>();
        Map<LocalDate, Integer> newUserCount = userService.getNewUserCount(museumId, beginDate, endDate);
        for (Map.Entry<LocalDate, Integer> entry : newUserCount.entrySet()) {
            userCounts.add(new DataAnalysisUserCountDTO(entry.getKey(), entry.getValue()));
        }

        return BaseResponse.ok(userCounts);
    }

    @ApiOperation("获取前K个热门展品及其热度值")
    @GetMapping("/trending-exhibit")
    public BaseResponse<List<DataAnalysisExhibitDTO>> getTopKHotExhibit(
            @ApiParam @Validated GetTopKTrendingReq req) {
        List<DataAnalysisExhibitDTO> exhibits = new ArrayList<>();
        Long museumId = req.getMuseumId();

        Map<Exhibit, Integer> topK = exhibitService.getTopKHotExhibit(museumId, req.getK());
        for (Map.Entry<Exhibit, Integer> entry : topK.entrySet()) {
            DataAnalysisExhibitDTO exhibitDTO = exhibitDTOMapper.toDto(entry.getKey());
            exhibitDTO.setTrendingScore(entry.getValue());
            exhibits.add(exhibitDTO);
        }

        return BaseResponse.ok(exhibits);
    }

    @ApiOperation("获取前K个热门展区及其热度值")
    @GetMapping("/trending-hall")
    public BaseResponse<List<DataAnalysisExhibitionHallDTO>> getTopKHotExhibitionHall(
            @ApiParam @Validated GetTopKTrendingReq req) {
        List<DataAnalysisExhibitionHallDTO> exhibitionHalls = new ArrayList<>();

        Long museumId = req.getMuseumId();

        Map<ExhibitionHall, Integer> topK = exhibitionHallService.getTopKHotExhibitionHall(museumId, req.getK());
        for (Map.Entry<ExhibitionHall, Integer> entry : topK.entrySet()) {
            DataAnalysisExhibitionHallDTO exhibitionHallDTO = exhibitionHallDTOMapper.toDto(entry.getKey());
            exhibitionHallDTO.setTrendingScore(entry.getValue());
            exhibitionHalls.add(exhibitionHallDTO);
        }

        return BaseResponse.ok(exhibitionHalls);
    }
}
