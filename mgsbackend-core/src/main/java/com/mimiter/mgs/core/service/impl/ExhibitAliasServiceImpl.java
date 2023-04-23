package com.mimiter.mgs.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mimiter.mgs.core.repository.ExhibitAliasRepository;
import com.mimiter.mgs.core.service.ExhibitAliasService;
import com.mimiter.mgs.model.entity.ExhibitAlias;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("exhibitAliasService")
public class ExhibitAliasServiceImpl extends ServiceImpl<ExhibitAliasRepository, ExhibitAlias> implements ExhibitAliasService {

    @Override
    public List<ExhibitAlias> getAllAliases(){
        return  baseMapper.getAllAlias();
    }
}
