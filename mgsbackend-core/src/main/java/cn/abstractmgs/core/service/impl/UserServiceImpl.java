package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.common.exception.BadRequestException;
import cn.abstractmgs.core.model.entity.User;
import cn.abstractmgs.core.model.entity.UserWxOpenid;
import cn.abstractmgs.core.model.param.PhoneLoginParam;
import cn.abstractmgs.core.model.param.WxLoginParam;
import cn.abstractmgs.core.model.response.Code2SessionResponse;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.repository.UserRepository;
import cn.abstractmgs.core.repository.UserWxOpenidRepository;
import cn.abstractmgs.core.service.SMSService;
import cn.abstractmgs.core.service.UserService;
import cn.abstractmgs.core.utils.EnvironmentUtil;
import cn.abstractmgs.core.utils.RedisUtil;
import cn.abstractmgs.core.utils.WxUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    private final SMSService smsService;

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
        User wxUser = getByOpenId(openid);
        if(wxUser == null){
            // 未绑定微信，验证短信并绑定微信
            wxUser = loginPhone(param);
            UserWxOpenid openid1 = new UserWxOpenid();
            openid1.setUserId(wxUser.getId());
            openid1.setWxOpenId(openid);
            openidRepository.insert(openid1);
        }

        return wxUser;
    }

    @Override
    public User loginPhone(PhoneLoginParam param) {
        if(!EnvironmentUtil.isTestEnv()){
            // 测试环境跳过验证短信
            Assert.isTrue(smsService.verifyCode(param.getPhoneNumber(), param.getVc(), param.getSms()), "短信验证码错误");
        }

        User user = getBaseMapper().getByPhoneNumber(param.getPhoneNumber());
        if(user == null){
            // 手机号未注册 通过手机号注册
            user = new User();
            user.setPhoneNumber(param.getPhoneNumber());
            user.setNickname("umt_"+param.getPhoneNumber());
            user.setAvatarUrl("");
            if(param instanceof WxLoginParam){
                WxLoginParam wxParam = (WxLoginParam) param;
                user.setNickname(wxParam.getNickname());
                user.setAvatarUrl(wxParam.getAvatarUrl());
            }
            getBaseMapper().insert(user);
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
        return userLocation == null ? null : Long.valueOf(String.valueOf(userLocation));
    }

    @Override
    public boolean isUserAtEndOfExhibitionHall(Long userId) {
        Long userLocation = getUserLocation(userId);
        if(userLocation == null){
            return false;
        }
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

    private void checkDuplicatePhone(String phone){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", phone);
        Long l = getBaseMapper().selectCount(wrapper);
        if(l != null && l > 0){
            throw new BadRequestException("手机号已被注册");
        }
    }
}
