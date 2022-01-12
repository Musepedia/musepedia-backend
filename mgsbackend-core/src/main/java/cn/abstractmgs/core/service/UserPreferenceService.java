package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.UserPreference;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserPreferenceService extends IService<UserPreference> {

    void deleteUserPreferenceById(Long userId);

    int insertUserPreferenceById(Long userId, List<Integer> hallIds);

    boolean updateUserPreference(Long userId, List<Integer> hallIds);
}
