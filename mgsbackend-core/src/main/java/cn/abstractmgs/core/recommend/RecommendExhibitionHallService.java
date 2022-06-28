package cn.abstractmgs.core.recommend;

import cn.abstractmgs.core.model.entity.ExhibitionHall;

import java.util.List;

public interface RecommendExhibitionHallService {

    /**
     * 获取推荐的展馆
     *
     * @param museumId 博物馆id
     * @param userPreference 用户兴趣
     * @param pos 当前位置
     * @return 推荐的展馆
     */
    ExhibitionHall getRecommendExhibitionHall(Long museumId, List<ExhibitionHall> userPreference, ExhibitionHall pos);
}
