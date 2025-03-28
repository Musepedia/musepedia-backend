package com.mimiter.mgs.core.recommend.impl;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.recommend.RecommendExhibitionHallService;
import com.mimiter.mgs.core.recommend.model.AreaSorter;
import com.mimiter.mgs.core.recommend.model.ExhibitionArea;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.mimiter.mgs.core.service.MuseumFloorPlanService;
import com.mimiter.mgs.core.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 推荐展厅服务实现类。
 */
@RequiredArgsConstructor
@Service("recommendExhibitionHallService")
public class RecommendExhibitionHallServiceImpl implements RecommendExhibitionHallService {

    private final MuseumFloorPlanService museumFloorPlanService;

    private final ExhibitionHallService exhibitionHallService;

    private final UserService userService;

    /**
     * {@inheritDoc}
     */
    public ExhibitionHall getRecommendExhibitionHall(Long museumId,
                                                     List<ExhibitionHall> userPreference,
                                                     ExhibitionHall pos) throws JsonProcessingException {
        List<ExhibitionHall> exhibitionHalls = exhibitionHallService.list(
                new LambdaQueryWrapper<>(ExhibitionHall.class)
                        .eq(ExhibitionHall::getMuseumId, museumId));

        HashMap<String, List<String>> neighbors = museumFloorPlanService.getMuseumFloorPlan(museumId);
        int len1 = exhibitionHalls.size();  //展区总数
        int len2 = userPreference.size(); //问卷长度
        AreaSorter sorter = new AreaSorter(new HashMap<>()); //排序器
        HashMap<String, Integer> map = new HashMap<>(); //哈希表
        for (int i = 0; i < len1; i++) {
            String name = String.valueOf(exhibitionHalls.get(i).getId());
            map.put(name, i);
            sorter.nameMap.put(name, new ExhibitionArea(name, 0, neighbors.get(name))); //存入展区名与展区的映射表
        }
        String[] qtnrRes = new String[len2];
        for (int i = 0; i < len2; i++) {
            qtnrRes[i] = userPreference.get(i).getId().toString();
        }
        String curName = pos.getId().toString();
        QuestionnaireAnaly qa = new QuestionnaireAnaly(qtnrRes); //问卷分析
        qa.listOperation(sorter.areaList); //根据问卷结果操作列表
        OperationByPosition obp = new OperationByPosition(sorter.nameMap.get(curName)); //当前位置分析
        obp.listOperation(sorter.areaList); //根据位置操作列表
        String recommended = sorter.recommend1(); //排序后输出第一位的展区名用于推荐
        return exhibitionHalls.get(map.get(recommended));
    }

    @Override
    public boolean isRecommendExhibitionHall(Long userId) {
        return userService.isUserAtEndOfExhibitionHall(userId) && userService.getUserRecommendStatus(userId);
    }
}
