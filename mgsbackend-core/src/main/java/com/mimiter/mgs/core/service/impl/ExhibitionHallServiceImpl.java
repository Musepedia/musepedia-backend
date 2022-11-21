package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.repository.ExhibitionHallRepository;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * {@inheritDoc}
 */
@Service("exhibitionHallService")
public class ExhibitionHallServiceImpl
        extends ServiceImpl<ExhibitionHallRepository, ExhibitionHall> implements ExhibitionHallService {

    private final int TRENDING = 10000;

    @Override
    public List<Integer> selectExhibitionHallIds(Long id) {
        return baseMapper.selectExhibitionHallIds(id);
    }

    @Override
    public Map<ExhibitionHall, Integer> getTopKHotExhibitionHall(Long museumId, int k) {
        Map<ExhibitionHall, Integer> topKExhibitionHalls = new HashMap<>();
        List<ExhibitionHall> exhibitCount = baseMapper.getExhibitCountPerExhibitionHall(museumId);
        List<ExhibitionHall> questionCount = baseMapper.getQuestionCountPerExhibitionHall(museumId);
        double ratioSum = 0;

        // 由于不是所有展区都有提问，因此exhibitCount一定不比questionCount小
        for (int i=0; i<exhibitCount.size(); ++i) {
            exhibitCount.get(i).setQuestionCount(0);
            for (int j=0; j<questionCount.size(); ++j) {
                if (exhibitCount.get(i).getId().equals(questionCount.get(j).getId())) {
                    exhibitCount.get(i).setQuestionCount(questionCount.get(j).getQuestionCount());
                    ratioSum += (double) exhibitCount.get(i).getQuestionCount() / exhibitCount.get(i).getExhibitCount();
                    break;
                }
            }
        }

        exhibitCount.sort(new Comparator<ExhibitionHall>() {
            @Override
            public int compare(ExhibitionHall hall1, ExhibitionHall hall2) {
                double diff = (double) hall1.getQuestionCount() / hall1.getExhibitCount() - (double) hall2.getQuestionCount() / hall2.getExhibitCount();
                if (diff > 0) {
                    return -1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

        k = Math.min(k, exhibitCount.size());
        for (int i=0; i<k; ++i) {
            topKExhibitionHalls.put(exhibitCount.get(i), (int) ((double) exhibitCount.get(i).getQuestionCount() / exhibitCount.get(i).getExhibitCount() / ratioSum * TRENDING));
        }
        return topKExhibitionHalls;
    }
}
