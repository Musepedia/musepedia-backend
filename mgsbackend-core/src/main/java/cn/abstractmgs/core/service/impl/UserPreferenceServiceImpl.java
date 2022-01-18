package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.ExhibitionHall;
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
    public void deleteByUserId(Long userId) {
        baseMapper.deleteByUserId(userId);
    }

    @Override
    public int createByUserId(Long userId, List<Long> hallIds) {
        return baseMapper.insertUserPreferenceById(userId, hallIds);
    }

    @Override
    public List<ExhibitionHall> getPreferredHallByUserId(Long userId) {
        return baseMapper.getPreferredHallByUserId(userId);
    }

    @Transactional
    @Override
    public boolean updateUserPreference(Long userId, List<Long> hallIds) {
        deleteByUserId(userId);
        if(hallIds.isEmpty()){
            return true;
        } else {
            int insertedRows = createByUserId(userId, hallIds);
            return insertedRows == hallIds.size();
        }
    }
}
