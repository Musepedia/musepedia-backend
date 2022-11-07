package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.model.entity.UserPreference;
import com.mimiter.mgs.core.repository.UserPreferenceRepository;
import com.mimiter.mgs.core.service.UserPreferenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userPreferenceService")
public class UserPreferenceServiceImpl
        extends ServiceImpl<UserPreferenceRepository, UserPreference>
        implements UserPreferenceService {

    @Override
    public void deleteByUserId(Long userId, Long museumId) {
        baseMapper.deleteByUserId(userId, museumId);
    }

    @Override
    public int createByUserId(Long userId, List<Long> hallIds) {
        return baseMapper.insertUserPreferenceById(userId, hallIds);
    }

    @Override
    public List<ExhibitionHall> getPreferredHallByUserId(Long userId, Long museumId) {
        return baseMapper.getPreferredHallByUserId(userId, museumId);
    }

    @Transactional
    @Override
    public boolean updateUserPreference(Long userId, List<Long> hallIds, Long museumId) {
        deleteByUserId(userId, museumId);
        if (!hallIds.isEmpty()) {
            int insertedRows = createByUserId(userId, hallIds);
            return insertedRows == hallIds.size();
        }
        return true;
    }
}
