package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 展区Service
 */
public interface ExhibitionHallService extends IService<ExhibitionHall> {

    /**
     * 获取博物馆的前K个热门展区，热度由展区中展品的平均提问量计算得到
     *
     * @param museumId 博物馆id
     * @param k 限定查询热门展区的数量
     * @return 博物馆的前K个热门展区，key为展区，value为展区的热度
     */
    Map<ExhibitionHall, Integer> getTopKHotExhibitionHall(Long museumId, int k);
}
