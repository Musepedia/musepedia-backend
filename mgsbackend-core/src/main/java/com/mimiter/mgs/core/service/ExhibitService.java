package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 展品Service
 */
public interface ExhibitService extends IService<Exhibit> {

    Exhibit getExhibitInfoById(Long id);

    List<String> selectRandomExhibitId(int limitPerExhibitionHall, Long museumId);

    List<Exhibit> selectRandomExhibits(List<Integer> ids);

    List<Exhibit> getRandomExhibits(int limitPerExhibitionHall, Long museumId);

    String selectExhibitFigureUrlByLabel(String label);

    Long selectExhibitionHallIdByExhibitId(Long id);

    Long selectExhibitIdByLabelAndMuseumId(String label, Long museumId);

    boolean isSameExhibitionHall(int id1, int id2);

    List<Exhibit> selectPreviousAndNextExhibitById(Long id);

    List<Exhibit> getMostFrequentExhibits(int count, Long museumId);

    /**
     * 获取博物馆的前K个热门展品，热度由展品的提问量计算得到
     *
     * @param museumId 博物馆id
     * @param k 限定查询热门展品的数量
     * @return 博物馆前K个热门展品，key为展品，value为展品的热度
     */
    Map<Exhibit, Integer> getTopKHotExhibit(Long museumId, int k);
}
