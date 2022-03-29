package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserPreferenceService extends IService<UserPreference> {

    void deleteByUserId(Long userId);

    int createByUserId(Long userId, List<Long> hallIds);

    List<ExhibitionHall> getPreferredHallByUserId(Long userId);

    boolean updateUserPreference(Long userId, List<Long> hallIds);
}
