package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.repository.ExhibitAliasRepository;
import com.mimiter.mgs.admin.service.ExhibitAliasService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import org.springframework.stereotype.Service;

@Service("exhibitAliasService")
public class ExhibitAliasServiceImpl extends
        AbstractCrudService<ExhibitAliasRepository, ExhibitAlias> implements ExhibitAliasService {

    @Override
    public boolean updateExhibitAlias(UpdateExhibitAliasReq req) {
        Long exhibitId = req.getExhibitId();
        getBaseMapper().deleteByExhibitId(exhibitId);

        for (String alias: req.getExhibitAlias()) {
            ExhibitAlias exhibitAlias = new ExhibitAlias();
            exhibitAlias.setExhibitId(exhibitId);
            exhibitAlias.setAlias(alias);
            save(exhibitAlias);
        }

        return true;
    }
}
