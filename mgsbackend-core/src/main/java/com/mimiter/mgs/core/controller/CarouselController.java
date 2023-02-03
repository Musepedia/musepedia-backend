package com.mimiter.mgs.core.controller;

import com.mimiter.mgs.common.annotation.AnonymousAccess;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.model.enums.CarouselType;
import com.mimiter.mgs.core.model.support.Carousel;
import com.mimiter.mgs.core.service.MuseumService;
import com.mimiter.mgs.model.entity.Museum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Api(value = "首页轮播图API", tags = {"首页轮播图API"})
@RestController
@RequestMapping("/api/carousel")
@RequiredArgsConstructor
public class CarouselController {

    private final MuseumService museumService;

    @SuppressWarnings("checkstyle:MagicNumber")
    @AnonymousAccess
    @GetMapping
    @ApiOperation("获取首页轮播图")
    public BaseResponse<List<Carousel>> getCarousel(
            @RequestParam(required = false, defaultValue = "museum") String type) {
        List<Carousel> res;
        switch (type) {
        case "exhibit":
            res = getExhibitCarousel();
            break;
        case "activity":
            res = new ArrayList<>();
            break;
        case "museum":
        default:
            res = getMuseumCarousel();
        }

        if (res.size() == 2) {
            Carousel c = new Carousel();
            c.setType(CarouselType.DISPLAY);
            c.setTitle("欢迎使用Musepedia博物馆导览系统");
            c.setDetail("Musepedia为博物馆导览提供了多场景解决方案，"
                    + "在参观博物馆时，您可以使用“问答”、“推荐”和“发现”等功能与我们自主研发和设计的Ginkgo系统交互，"
                    + "了解展品详细信息，提升游览体验。");
            c.setImg("https://static.musepedia.cn/figs/mgsframe.png");
            res.add(0, c);
        }

        return BaseResponse.ok(res);
    }

    private List<Carousel> getMuseumCarousel() {
        List<Carousel> res = new ArrayList<>();
        List<Museum> museums = museumService.selectAllServicedMuseums();
        for (Museum museum : museums) {
            Carousel carousel = new Carousel();
            carousel.setTitle(museum.getName());
            carousel.setDetail(museum.getDescription());
            carousel.setImg(museum.getImageUrl());
            carousel.setType(CarouselType.DISPLAY);
            res.add(carousel);
        }
        return res;
    }

    private List<Carousel> getExhibitCarousel() {
        List<Carousel> res = new ArrayList<>();

        return res;
    }
}
