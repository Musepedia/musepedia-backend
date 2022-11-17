package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.core.repository.ExhibitRepository;
import com.mimiter.mgs.core.service.ExhibitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Service("exhibitService")
public class ExhibitServiceImpl extends ServiceImpl<ExhibitRepository, Exhibit> implements ExhibitService {

    private List<Integer> exhibitIdToInteger(List<String> exhibitIds) {
        List<Integer> res = new ArrayList<>();
        for (String ids : exhibitIds) {
            for (String id : ids.split(",")) {
                res.add(Integer.parseInt(id));
            }
        }

        return res;
    }

    @Override
    public Exhibit getExhibitInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    public List<String> selectRandomExhibitId(int limitPerExhibitionHall, Long museumId) {
        return baseMapper.selectRandomExhibitId(limitPerExhibitionHall, museumId);
    }

    @Override
    public List<Exhibit> selectRandomExhibits(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return baseMapper.selectRandomExhibits(ids);
    }

    @Override
    public List<Exhibit> getRandomExhibits(int limitPerExhibitionHall, Long museumId) {
        List<Integer> exhibitIds = exhibitIdToInteger(selectRandomExhibitId(limitPerExhibitionHall, museumId));
        return selectRandomExhibits(exhibitIds);
    }

    @Override
    public String selectExhibitFigureUrlByLabel(String label) {
        return baseMapper.selectExhibitFigureUrlByLabel(label);
    }

    @Override
    public Long selectExhibitionHallIdByExhibitId(Long id) {
        return baseMapper.selectExhibitionHallIdByExhibitId(id);
    }

    @Override
    public Long selectExhibitIdByLabelAndMuseumId(String label, Long museumId) {
        return baseMapper.selectExhibitIdByLabelAndMuseumId(label, museumId);
    }

    @Override
    public boolean isSameExhibitionHall(int id1, int id2) {
        return baseMapper.isSameExhibitionHall(id1, id2);
    }

    @Override
    public List<Exhibit> selectPreviousAndNextExhibitById(Long id) {
        return baseMapper.selectPreviousAndNextExhibitById(id);
    }

    @Override
    public List<Exhibit> getMostFrequentExhibits(int count, Long museumId) {
        return baseMapper.selectMostFrequentExhibits(count, museumId);
    }


}
