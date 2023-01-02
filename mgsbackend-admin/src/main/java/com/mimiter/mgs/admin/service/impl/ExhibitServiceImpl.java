package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.repository.ExhibitRepository;
import com.mimiter.mgs.admin.service.ExhibitService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.Exhibit;
import org.springframework.stereotype.Service;

@Service("exhibitService")
public class ExhibitServiceImpl extends AbstractCrudService<ExhibitRepository, Exhibit> implements ExhibitService {

}
