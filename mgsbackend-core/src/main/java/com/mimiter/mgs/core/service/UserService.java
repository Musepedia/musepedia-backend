package com.mimiter.mgs.core.service;

import com.mimiter.mgs.core.model.entity.User;
import com.mimiter.mgs.core.model.param.PhoneLoginParam;
import com.mimiter.mgs.core.model.param.WxLoginParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 用户服务
 */
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

    /**
     * 获取<code>date</code>日期当天博物馆的用户新增数量
     * @param museumId 博物馆id
     * @param date 查询的日期
     * @return 查询日期当天博物馆的用户新增数量
     */
    int getNewUserCount(Long museumId, LocalDate date);

    /**
     * 获取从<code>beginDate</code>到<code>endDate</code>范围内，每天博物馆的用户新增数量
     * @param museumId 博物馆id
     * @param beginDate 开始日期
     * @param endDate 终止日期
     * @return 从开始日期到终止日期内每天博物馆的用户新增数量，key为日期，value为该日期用户新增数量
     */
    Map<LocalDate, Integer> getNewUserCount(Long museumId, LocalDate beginDate, LocalDate endDate);

}
