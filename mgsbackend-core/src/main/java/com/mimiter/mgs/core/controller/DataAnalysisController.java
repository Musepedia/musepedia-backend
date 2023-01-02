package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.dto.*;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.mimiter.mgs.core.service.UserService;
import com.mimiter.mgs.core.service.mapstruct.*;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/data-analysis")
@RequiredArgsConstructor
public class DataAnalysisController {

    private final UserService userService;

    private final ExhibitService exhibitService;

    private final ExhibitionHallService exhibitionHallService;

    private final DataAnalysisExhibitDTOMapper exhibitDTOMapper;

    private final DataAnalysisExhibitionHallDTOMapper exhibitionHallDTOMapper;


    @ApiOperation("获取某天的用户新增数量")
    @GetMapping("/user/date")
    public BaseResponse<DataAnalysisUserCountDTO> getNewUserCount(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Long museumId = ThreadContextHolder.getCurrentMuseumId();

        return BaseResponse.ok(new DataAnalysisUserCountDTO(date, userService.getNewUserCount(museumId, date)));
    }

    @ApiOperation("获取一段时间的用户新增数量")
    @GetMapping("/user")
    public BaseResponse<List<DataAnalysisUserCountDTO>> getNewUserCount(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate beginDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<DataAnalysisUserCountDTO> userCounts = new ArrayList<>();
        Long museumId = ThreadContextHolder.getCurrentMuseumId();

        Map<LocalDate, Integer> newUserCount = userService.getNewUserCount(museumId, beginDate, endDate);
        for (Map.Entry<LocalDate, Integer> entry : newUserCount.entrySet()) {
            userCounts.add(new DataAnalysisUserCountDTO(entry.getKey(), entry.getValue()));
        }

        return BaseResponse.ok(userCounts);
    }

    @ApiOperation("获取前K个热门展品及其热度值")
    @GetMapping("/exhibit")
    public BaseResponse<List<DataAnalysisExhibitDTO>> getTopKHotExhibit(@RequestParam int k) {
        List<DataAnalysisExhibitDTO> exhibits = new ArrayList<>();
        Long museumId = ThreadContextHolder.getCurrentMuseumId();

        Map<Exhibit, Integer> topK = exhibitService.getTopKHotExhibit(museumId, k);
        for (Map.Entry<Exhibit, Integer> entry : topK.entrySet()) {
            DataAnalysisExhibitDTO exhibitDTO = exhibitDTOMapper.toDto(entry.getKey());
            exhibitDTO.setTrendingScore(entry.getValue());
            exhibits.add(exhibitDTO);
        }

        return BaseResponse.ok(exhibits);
    }

    @ApiOperation("获取前K个热门展区及其热度值")
    @GetMapping("/exhibition-hall")
    public BaseResponse<List<DataAnalysisExhibitionHallDTO>> getTopKHotExhibitionHall(@RequestParam int k) {
        List<DataAnalysisExhibitionHallDTO> exhibitionHalls = new ArrayList<>();

        Long museumId = ThreadContextHolder.getCurrentMuseumId();

        Map<ExhibitionHall, Integer> topK = exhibitionHallService.getTopKHotExhibitionHall(museumId, k);
        for (Map.Entry<ExhibitionHall, Integer> entry : topK.entrySet()) {
            DataAnalysisExhibitionHallDTO exhibitionHallDTO = exhibitionHallDTOMapper.toDto(entry.getKey());
            exhibitionHallDTO.setTrendingScore(entry.getValue());
            exhibitionHalls.add(exhibitionHallDTO);
        }

        return BaseResponse.ok(exhibitionHalls);
    }
}
