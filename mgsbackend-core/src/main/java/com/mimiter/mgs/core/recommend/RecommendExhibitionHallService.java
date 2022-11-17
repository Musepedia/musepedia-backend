package com.mimiter.mgs.core.recommend;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * 推荐展区接口
 */
public interface RecommendExhibitionHallService {

    /**
     * 获取推荐的展馆
     *
     * @param museumId       博物馆id
     * @param userPreference 用户兴趣
     * @param pos            当前位置
     * @return 推荐的展馆
     */
    ExhibitionHall getRecommendExhibitionHall(Long museumId,
                                              List<ExhibitionHall> userPreference,
                                              ExhibitionHall pos) throws JsonProcessingException;

    boolean isRecommendExhibitionHall(Long userId);
}
