package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.support.Carousel;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(value = "首页轮播图API", tags = {"首页轮播图API"})
@RestController
@RequestMapping("/api/carousel")
@RequiredArgsConstructor
public class CarouselController {

    @AnonymousAccess
    @GetMapping
    public BaseResponse<List<Carousel>> getCarousel() {
        // TODO 轮播图策略
        return BaseResponse.ok();
    }
}
