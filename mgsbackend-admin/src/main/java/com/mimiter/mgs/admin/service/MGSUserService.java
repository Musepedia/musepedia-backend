package com.mimiter.mgs.admin.service;

import java.time.LocalDate;
import java.util.Map;

/**
 * MGS博物馆导览业务的用户服务
 */
public interface MGSUserService {

    /**
     * 获取<code>date</code>日期当天博物馆的用户新增数量
     *
     * @param museumId 博物馆id
     * @param date 查询的日期
     * @return 查询日期当天博物馆的用户新增数量
     */
    int getNewUserCount(Long museumId, LocalDate date);

    /**
     * 获取从<code>beginDate</code>到<code>endDate</code>范围内，每天博物馆的用户新增数量
     *
     * @param museumId 博物馆id
     * @param beginDate 开始日期
     * @param endDate 终止日期
     * @return 从开始日期到终止日期内每天博物馆的用户新增数量，key为日期，value为该日期用户新增数量
     */
    Map<LocalDate, Integer> getNewUserCount(Long museumId, LocalDate beginDate, LocalDate endDate);
}
