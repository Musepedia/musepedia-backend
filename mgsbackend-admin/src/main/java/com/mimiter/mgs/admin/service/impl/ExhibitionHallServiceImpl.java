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
}
