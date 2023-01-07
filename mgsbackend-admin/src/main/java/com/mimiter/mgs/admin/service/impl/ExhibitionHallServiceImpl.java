package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.repository.ExhibitionHallRepository;
import com.mimiter.mgs.admin.service.ExhibitionHallService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.ExhibitionHall;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("exhibitHallService")
public class ExhibitionHallServiceImpl extends
        AbstractCrudService<ExhibitionHallRepository, ExhibitionHall> implements ExhibitionHallService {
    @Override
    public ExhibitionHall addExhibitionHall(UpsertExhibitionHallReq req) {
        Assert.isNull(getBaseMapper().findByName(req.getName()), "展区已存在");

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
}
