package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.core.repository.MuseumRepository;
import com.mimiter.mgs.core.service.MuseumService;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("museumService")
public class MuseumServiceImpl extends ServiceImpl<MuseumRepository, Museum> implements MuseumService {

    @Override
    public List<Museum> selectAllServicedMuseums() {
        return baseMapper.selectAllServicedMuseums();
    }

    @Override
    public boolean hasPermission(Long museumId, int permission) {
        if (museumId == null) {
            return false;
        }
        Museum museum = getById(museumId);
        return museum != null && museum.hasPermission(permission);
    }
}
