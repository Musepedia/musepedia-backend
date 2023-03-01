package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 展品Service
 */
public interface ExhibitService extends IService<Exhibit> {

    List<String> selectRandomExhibitId(int limitPerExhibitionHall, Long museumId);

    List<Exhibit> selectRandomExhibits(List<Integer> ids);

    List<Exhibit> getRandomExhibits(int limitPerExhibitionHall, Long museumId);

    String selectExhibitFigureUrlByLabel(String label);

    Long selectExhibitionHallIdByExhibitId(Long id);

    Long selectExhibitIdByLabelAndMuseumId(String label, Long museumId);

    boolean isSameExhibitionHall(int id1, int id2);

    List<Exhibit> selectPreviousAndNextExhibitById(Long id);

    List<Exhibit> getMostFrequentExhibits(int count, Long museumId);

}
