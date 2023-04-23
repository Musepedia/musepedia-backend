package com.mimiter.mgs.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mimiter.mgs.model.entity.ExhibitAlias;

import java.util.List;

public interface ExhibitAliasService extends IService<ExhibitAlias> {
    List<ExhibitAlias> getAllAliases();
}