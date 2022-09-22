package com.mimiter.mgs.core.service;

import com.mimiter.mgs.core.model.entity.User;
import com.mimiter.mgs.core.model.param.PhoneLoginParam;
import com.mimiter.mgs.core.model.param.WxLoginParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {

    User getByOpenId(String openid);

    User loginWx(WxLoginParam param);

    User loginPhone(PhoneLoginParam param);

    void setUserLocation(Long userId, Long exhibitionHallId);

    Long getUserLocation(Long userId);

    boolean isUserAtEndOfExhibitionHall(Long userId);

    void setUserRecommendStatus(Long userId, boolean recommendStatus);

    boolean getUserRecommendStatus(Long userId);

    List<User> ageWithLabels(Long museumId);

}
