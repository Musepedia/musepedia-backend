package com.mimiter.mgs.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.mapstruct.ExhibitDTOMapper;
import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.repository.ExhibitRepository;
import com.mimiter.mgs.admin.repository.ExhibitionHallRepository;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("exhibitService")
@RequiredArgsConstructor
public class ExhibitServiceImpl extends
        AbstractCrudService<ExhibitRepository, Exhibit> implements ExhibitService {

    private static final int TRENDING = 10000;

    private final ExhibitionHallRepository exhibitionHallRepository;

    private final ExhibitTextService exhibitTextService;

    private final ExhibitDTOMapper exhibitDTOMapper;

    @Override
    @Transactional
    public Exhibit addExhibit(UpsertExhibitReq req) {
        Assert.isNull(getBaseMapper().findByLabel(req.getLabel()), "展品已存在");

        QueryWrapper<ExhibitionHall> exhibitionHallQueryWrapper = new QueryWrapper<>();
        exhibitionHallQueryWrapper.eq("exhibition_hall_id", req.getHallId());
        ExhibitionHall exhibitionHall = exhibitionHallRepository.selectOne(exhibitionHallQueryWrapper);
        Assert.notNull(exhibitionHall, "展区不存在");

        Exhibit exhibit = new Exhibit();
        exhibit.setHallId(req.getHallId());
        exhibit.setUrl(req.getUrl());
        exhibit.setLabel(req.getLabel());
        exhibit.setDescription(req.getDescription());
        exhibit.setFigureUrl(req.getFigureUrl());
        exhibit.setFigureUrlList(req.getFigureUrlList());
        exhibit.setPrevId(req.getPrevId());
        exhibit.setNextId(req.getNextId());
        exhibit.setMuseumId(exhibitionHall.getMuseumId());
        save(exhibit);

        // 将描述保存到exhibit_text表中
        ExhibitText text = new ExhibitText();
        text.setExhibitId(exhibit.getId());
        text.setText(exhibit.getDescription());
        exhibitTextService.save(text);

        return exhibit;
    }

    @Override
    public boolean updateExhibit(UpsertExhibitReq req) {
        QueryWrapper<ExhibitionHall> exhibitionHallQueryWrapper = new QueryWrapper<>();
        exhibitionHallQueryWrapper.eq("exhibition_hall_id", req.getHallId());
        ExhibitionHall exhibitionHall = exhibitionHallRepository.selectOne(exhibitionHallQueryWrapper);
        Assert.notNull(exhibitionHall, "展区不存在");

        Exhibit exhibit = new Exhibit();
        exhibit.setId(req.getId());
        exhibit.setDescription(req.getDescription());
        exhibit.setFigureUrl(req.getFigureUrl());
        exhibit.setFigureUrlList(req.getFigureUrlList());
        exhibit.setPrevId(req.getPrevId());
        exhibit.setHallId(req.getHallId());
        exhibit.setUrl(req.getUrl());
        exhibit.setLabel(req.getLabel());
        exhibit.setNextId(req.getNextId());
        updateById(exhibit);

        return true;
    }

    @Override
    public ExhibitDTO toDTO(Exhibit exhibit) {
        if (exhibit == null) {
            return null;
        }
        return exhibitDTOMapper.toDto(exhibit);
    }

    @Override
    public Map<Exhibit, Integer> getTopKHotExhibit(Long museumId, int k) {
        Map<Exhibit, Integer> topKExhibits = new HashMap<>();
        List<Exhibit> questionCount = baseMapper.getQuestionCountPerExhibit(museumId);
        int sumQuestion = questionCount.stream().mapToInt(Exhibit::getQuestionCount).sum();

        questionCount.sort(new Comparator<Exhibit>() {
            @Override
            public int compare(Exhibit exhibit1, Exhibit exhibit2) {
                return exhibit2.getQuestionCount() - exhibit1.getQuestionCount();
            }
        });

        k = Math.min(k, questionCount.size());
        for (int i = 0; i < k; ++i) {
            topKExhibits.put(questionCount.get(i),
                    (int) ((double) questionCount.get(i).getQuestionCount() / sumQuestion * TRENDING));
        }

        return topKExhibits;
    }
}
