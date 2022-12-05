package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.repository.CulturalCreativeRepository;
import com.mimiter.mgs.admin.service.CulturalCreativeSerivce;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.CulturalCreative;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("culturalCreativeService")
@RequiredArgsConstructor
public class CulturalCreativeServiceImpl
        extends AbstractCrudService<CulturalCreativeRepository, CulturalCreative>
        implements CulturalCreativeSerivce {


}
