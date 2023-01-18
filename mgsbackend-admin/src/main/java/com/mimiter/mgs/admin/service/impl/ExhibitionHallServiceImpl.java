package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mimiter.mgs.admin.mapstruct.ExhibitionHallDTOMapper;
import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.ExhibitRepository;
import com.mimiter.mgs.admin.repository.ExhibitionHallRepository;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("exhibitHallService")
@RequiredArgsConstructor
public class ExhibitionHallServiceImpl extends
        AbstractCrudService<ExhibitionHallRepository, ExhibitionHall> implements ExhibitionHallService {

    private final ExhibitionHallDTOMapper exhibitionHallDTOMapper;

    private final ExhibitRepository exhibitRepository;

    @Override
    public ExhibitionHall addExhibitionHall(UpsertExhibitionHallReq req) {
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setName(req.getName());
        exhibitionHall.setDescription(req.getDescription());
        exhibitionHall.setImageUrl(req.getImageUrl());
        exhibitionHall.setImageList(req.getImageList());
        exhibitionHall.setMuseumId(req.getMuseumId());
        save(exhibitionHall);

        return exhibitionHall;
    }

    @Override
    public boolean updateExhibitionHall(UpsertExhibitionHallReq req) {

        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setId(req.getId());
        exhibitionHall.setName(req.getName());
        exhibitionHall.setDescription(req.getDescription());
        exhibitionHall.setImageUrl(req.getImageUrl());
        exhibitionHall.setImageList(req.getImageList());
        exhibitionHall.setMuseumId(req.getMuseumId());
        updateById(exhibitionHall);

        return true;
    }

    @Transactional
    @Override
    public void setExhibitionHallEnable(SetEnableReq req) {
        Assert.notNull(req.getId(), "展区id不能为空");
        Assert.notNull(req.getEnable(), "展区是否启用不能为空");
        ExhibitionHall exhibitionHall = new ExhibitionHall();
        exhibitionHall.setId(req.getId());
        exhibitionHall.setEnabled(req.getEnable());
        updateById(exhibitionHall);

        UpdateWrapper<Exhibit> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("exhibition_hall_id", req.getId());
        updateWrapper.set("is_enabled", req.getEnable());
        exhibitRepository.update(null, updateWrapper);
    }

    @Override
    public ExhibitionHallDTO toDTO(ExhibitionHall exhibitionHall) {
        if (exhibitionHall == null) {
            return null;
        }
        return exhibitionHallDTOMapper.toDto(exhibitionHall);
    }

    private static final int TRENDING = 10000;

    @Override
    public Map<ExhibitionHall, Integer> getTopKHotExhibitionHall(Long museumId, int k) {
        Map<ExhibitionHall, Integer> topKExhibitionHalls = new HashMap<>();
        List<ExhibitionHall> exhibitCount = baseMapper.getExhibitCountPerExhibitionHall(museumId);
        List<ExhibitionHall> questionCount = baseMapper.getQuestionCountPerExhibitionHall(museumId);
        double ratioSum = 0;

        // 由于不是所有展区都有提问，因此exhibitCount一定不比questionCount小
        for (int i = 0; i < exhibitCount.size(); ++i) {
            exhibitCount.get(i).setQuestionCount(0);
            for (int j = 0; j < questionCount.size(); ++j) {
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
                double diff = (double) hall1.getQuestionCount() / hall1.getExhibitCount()
                        - (double) hall2.getQuestionCount() / hall2.getExhibitCount();
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
        for (int i = 0; i < k; ++i) {
            topKExhibitionHalls.put(exhibitCount.get(i),
                    (int) ((double) exhibitCount.get(i).getQuestionCount()
                            / exhibitCount.get(i).getExhibitCount() / ratioSum * TRENDING));
        }
        return topKExhibitionHalls;
    }
}
