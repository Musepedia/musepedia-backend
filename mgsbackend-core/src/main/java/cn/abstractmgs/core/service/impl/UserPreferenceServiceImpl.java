package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.UserPreference;
import cn.abstractmgs.core.repository.UserPreferenceRepository;
import cn.abstractmgs.core.service.UserPreferenceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("userPreferenceService")
public class UserPreferenceServiceImpl extends ServiceImpl<UserPreferenceRepository, UserPreference> implements UserPreferenceService {

    @Override
    public void deleteUserPreferenceById(Long userId) {
        baseMapper.deleteUserPreferenceById(userId);
    }

    @Override
    public int insertUserPreferenceById(Long userId, List<Integer> hallIds) {
        return baseMapper.insertUserPreferenceById(userId, hallIds);
    }

    public boolean updateUserPreference(Long userId, List<Integer> hallIds) {
        deleteUserPreferenceById(userId);
        int insertedRows = insertUserPreferenceById(userId, hallIds);

        return insertedRows == hallIds.size();
    }
}
