package com.mimiter.mgs.core.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.repository.CulturalCreativeRepository;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.mimiter.mgs.model.entity.CulturalCreative;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creative")
@RequiredArgsConstructor
@Api(value = "文创API", tags = {"文创API"})
public class CulturalCreativeController {

    private final CulturalCreativeRepository culturalCreativeRepository;

    @GetMapping("/random")
    @ApiOperation("获取当前博物馆的随机文创")
    public BaseResponse<CulturalCreative> random(@RequestParam Long museumId) {
        QueryWrapper<CulturalCreative> wrapper = new QueryWrapper<>();
        wrapper.eq("museum_id", museumId);
        wrapper.eq("is_deleted", false);
        wrapper.orderByDesc("rand()");
        wrapper.last("limit 1");
        CulturalCreative culturalCreative = culturalCreativeRepository.selectOne(wrapper);

        return BaseResponse.ok(culturalCreative);
    }
}
