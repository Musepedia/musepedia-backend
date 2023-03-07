package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.common.exception.BadRequestException;
import com.mimiter.mgs.common.model.BaseResponse;
import com.mimiter.mgs.core.utils.SecurityUtil;
import com.mimiter.mgs.model.entity.User;
import com.mimiter.mgs.core.model.entity.UserWxOpenid;
import com.mimiter.mgs.core.model.param.PhoneLoginParam;
import com.mimiter.mgs.core.model.param.WxLoginParam;
import com.mimiter.mgs.core.model.response.Code2SessionResponse;
import com.mimiter.mgs.core.repository.ExhibitRepository;
import com.mimiter.mgs.core.repository.UserRepository;
import com.mimiter.mgs.core.repository.UserWxOpenidRepository;
import com.mimiter.mgs.core.service.SMSService;
import com.mimiter.mgs.core.service.UserService;
import com.mimiter.mgs.common.utils.EnvironmentUtil;
import com.mimiter.mgs.common.utils.RedisUtil;
import com.mimiter.mgs.core.utils.WxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserRepository, User> implements UserService {

    private final WxUtil wxUtil;

    private final UserWxOpenidRepository openidRepository;

    private final ExhibitRepository exhibitRepository;

    private final RedisUtil redisUtil;

    private final SMSService smsService;

    @Override
    public User getByUnionid(String openid) {
        return getBaseMapper().getByUnionid(openid);
    }

    private UserWxOpenid getOpenIdFromCode2Session(String code) {
        Code2SessionResponse resp = wxUtil.code2Session(code);
        Assert.isTrue(resp != null && StringUtils.hasText(resp.getOpenid()), "获取openid失败");

        UserWxOpenid res = new UserWxOpenid();
        res.setWxOpenId(resp.getOpenid());
        res.setWxUnionId(resp.getUnionid());
        return res;
    }

    @Override
    public String getUserOpenId(String code) {
        Long userId = SecurityUtil.getCurrentUserId();
        Assert.notNull(userId, "未检测到登录用户");
        QueryWrapper<UserWxOpenid> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        UserWxOpenid wxUser = openidRepository.selectOne(wrapper);
        if (wxUser != null && wxUser.getWxOpenId() != null) {
            // 之前只保存了unionId的用户
            return wxUser.getWxOpenId();
        }

        // 没有UnionId/OpenId信息
        Assert.hasText(code, "code不能为空");
        UserWxOpenid idFromCode2Session = getOpenIdFromCode2Session(code);
        if (wxUser == null) {
            idFromCode2Session.setUserId(userId);
            openidRepository.insert(idFromCode2Session);
        } else {
            wxUser.setWxOpenId(idFromCode2Session.getWxOpenId());
            wxUser.setWxUnionId(idFromCode2Session.getWxUnionId());
            openidRepository.updateById(wxUser);
        }

        return idFromCode2Session.getWxOpenId();
    }

    @Transactional
    @Override
    public User loginWx(WxLoginParam param) {
        Code2SessionResponse resp = wxUtil.code2Session(param.getCode());
        Assert.isTrue(resp != null && StringUtils.hasText(resp.getOpenid()), "获取openid失败");

        String unionid = resp.getUnionid();
        User wxUser = getByUnionid(unionid);
        if (wxUser == null) {
            // 未绑定微信，验证短信并绑定微信
            wxUser = loginPhone(param);
            UserWxOpenid openid1 = new UserWxOpenid();
            openid1.setUserId(wxUser.getId());
            openid1.setWxOpenId(resp.getOpenid());
            openid1.setWxUnionId(unionid);
            openidRepository.insert(openid1);
        }

        return wxUser;
    }

    @Override
    public User loginPhone(PhoneLoginParam param) {
        if (!EnvironmentUtil.isTestEnv()) {
            // 测试环境跳过验证短信
            Assert.isTrue(smsService.verifyCode(param.getPhoneNumber(), param.getVc(), param.getSms()), "短信验证码错误");
        }

        User user = getBaseMapper().getByPhoneNumber(param.getPhoneNumber());
        if (user == null) {
            // 手机号未注册 通过手机号注册
            user = new User();
            user.setPhoneNumber(param.getPhoneNumber());
            user.setNickname("pedia_" + param.getPhoneNumber());
            user.setAvatarUrl("");
            if (param instanceof WxLoginParam) {
                WxLoginParam wxParam = (WxLoginParam) param;
                user.setAvatarUrl(wxParam.getAvatarUrl());
            }
            getBaseMapper().insert(user);
        }

        return user;
    }

    @Override
    public void setUserLocation(Long userId, Long exhibitionHallId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(exhibitionHallId, "exhibitionHallId must not be null");
        if (!exhibitionHallId.equals(getUserLocation(userId))) {
            redisUtil.set(
                    redisUtil.getKey("user", userId, "location"),
                    exhibitionHallId, 1, TimeUnit.DAYS);
            setUserRecommendStatus(userId, true);
        }
    }

    @Override
    public Long getUserLocation(Long userId) {
        Object userLocation = redisUtil.get(redisUtil.getKey("user", userId, "location"));
        return userLocation != null ? Long.valueOf(String.valueOf(userLocation)) : null;
    }

    @Override
    public boolean isUserAtEndOfExhibitionHall(Long userId) {
        Long userLocation = getUserLocation(userId);
        if (userLocation == null) {
            return false;
        }
        final double percentage = 0.1;
        List<Long> exhibits = exhibitRepository.selectExhibitIdsInSameExhibitionHall(userLocation);

        int countOfExhibits = (int) Math.ceil(percentage * exhibits.size());

        for (int i = exhibits.size() - 1; i >= 0 && countOfExhibits >= 0; --i, --countOfExhibits) {
            if (userLocation.equals(exhibits.get(i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setUserRecommendStatus(Long userId, boolean recommendStatus) {
        redisUtil.set(
                redisUtil.getKey("user", userId, "recommendStatus"),
                recommendStatus, 1, TimeUnit.DAYS);
    }

    @Override
    public boolean getUserRecommendStatus(Long userId) {
        Object status = redisUtil.get(redisUtil.getKey("user", userId, "recommendStatus"));
        return (Boolean) status;
    }

    private void checkDuplicatePhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", phone);
        Long l = getBaseMapper().selectCount(wrapper);
        if (l != null && l > 0) {
            throw new BadRequestException("手机号已被注册");
        }
    }

    public List<User> ageWithLabels(Long museumId) {
        return baseMapper.ageWithLabels(museumId);
    }

}
