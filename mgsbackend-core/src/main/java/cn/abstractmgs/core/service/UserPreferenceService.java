package cn.abstractmgs.core.service;

import cn.abstractmgs.model.entity.ExhibitionHall;
import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserPreferenceService extends IService<UserPreference> {

    void deleteByUserId(Long userId, Long museumId);

    int createByUserId(Long userId, List<Long> hallIds);

    List<ExhibitionHall> getPreferredHallByUserId(Long userId, Long museumId);

    boolean updateUserPreference(Long userId, List<Long> hallIds, Long museumId);
}
