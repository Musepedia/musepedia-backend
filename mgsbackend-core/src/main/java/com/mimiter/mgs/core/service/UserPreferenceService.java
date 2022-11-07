package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户偏好服务
 */
public interface UserPreferenceService extends IService<UserPreference> {

    void deleteByUserId(Long userId, Long museumId);

    int createByUserId(Long userId, List<Long> hallIds);

    List<ExhibitionHall> getPreferredHallByUserId(Long userId, Long museumId);

    boolean updateUserPreference(Long userId, List<Long> hallIds, Long museumId);
}
