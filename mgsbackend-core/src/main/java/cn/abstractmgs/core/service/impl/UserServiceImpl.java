package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.UserWxOpenid;
import cn.abstractmgs.core.model.entity.enums.AgeEnum;
import cn.abstractmgs.core.model.entity.enums.GenderEnum;
import cn.abstractmgs.core.model.response.Code2SessionResponse;
import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.param.WxLoginParam;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.repository.UserRepository;
import cn.abstractmgs.core.repository.UserWxOpenidRepository;
import cn.abstractmgs.core.service.UserService;
import cn.abstractmgs.core.utils.RedisUtil;
import cn.abstractmgs.core.utils.WxUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {

    private final WxUtil wxUtil;

    private final UserWxOpenidRepository openidRepository;

    private final ExhibitRepository exhibitRepository;

    private final RedisUtil redisUtil;

    @Override
    public User getByOpenId(String openid) {
        return getBaseMapper().getByOpenid(openid);
    }

    @Transactional
    @Override
    public User loginWx(WxLoginParam param) {
        Code2SessionResponse resp = wxUtil.code2Session(param);
        Assert.isTrue(resp != null && StringUtils.hasText(resp.getOpenid()), "获取openid失败");

        String openid = resp.getOpenid();
        User user = getByOpenId(openid);
        if(user == null){
            user = new User();
            Assert.isTrue(
                    StringUtils.hasText(param.getNickname())
                            && StringUtils.hasText(param.getAvatarUrl()), "注册昵称和头像不能为空");
            user.setNickname(param.getNickname());
            user.setAvatarUrl(param.getAvatarUrl());
            getBaseMapper().insert(user);

            UserWxOpenid openid1 = new UserWxOpenid();
            openid1.setUserId(user.getId());
            openid1.setWxOpenId(openid);
            openidRepository.insert(openid1);
        }

        return user;
    }

    @Override
    public void setUserLocation(Long userId, Long exhibitionHallId) {
        redisUtil.set(redisUtil.getKey("user", userId, "location"), exhibitionHallId);
    }

    @Override
    public Long getUserLocation(Long userId) {
        Object userLocation = redisUtil.get(redisUtil.getKey("user", userId, "location"));
        return Long.valueOf(String.valueOf(userLocation));
    }

    @Override
    public boolean isUserAtEndOfExhibitionHall(Long userId) {
        Long userLocation = getUserLocation(userId);
        double percentage = 0.1;
        List<Long> exhibits = exhibitRepository.selectExhibitIdsInSameExhibitionHall(userLocation);

        int countOfExhibits = (int)Math.ceil(percentage * exhibits.size());

        for (int i=exhibits.size()-1; countOfExhibits >= 0; --i, --countOfExhibits) {
            if (userLocation.equals(exhibits.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> ageWithLabels(Long museumId) {
        return baseMapper.ageWithLabels(museumId);
    }

}
