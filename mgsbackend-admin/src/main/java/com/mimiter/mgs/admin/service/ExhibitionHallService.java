package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.dto.ExhibitionHallDTO;
import com.mimiter.mgs.admin.model.request.SetEnableReq;
import com.mimiter.mgs.admin.model.request.UpsertExhibitionHallReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.model.entity.ExhibitionHall;

import java.util.Map;

/**
 * 展区服务
 */
public interface ExhibitionHallService extends CrudService<ExhibitionHall> {

    /**
     * 添加展区
     *
     * @param req 添加展区请求
     * @return 添加的展区
     */
    ExhibitionHall addExhibitionHall(UpsertExhibitionHallReq req);

    /**
     * 更新展区
     *
     * @param req 更新展区请求
     * @return 是否更新成功
     */
    boolean updateExhibitionHall(UpsertExhibitionHallReq req);

    /**
     * 设置展区是否启用
     *
     * @param req 设置展区是否启用请求
     */
    void setExhibitionHallEnable(SetEnableReq req);

    /**
     * 转换为ExhibitionHallDTO
     *
     * @param exhibitionHall /
     * @return null如果ExhibitionHall是null，否则返回转换后的ExhibitionHallDTO
     */
    ExhibitionHallDTO toDTO(ExhibitionHall exhibitionHall);

    /**
     * 获取博物馆的前K个热门展区，热度由展区中展品的平均提问量计算得到
     *
     * @param museumId 博物馆id
     * @param k 限定查询热门展区的数量
     * @return 博物馆的前K个热门展区，key为展区，value为展区的热度
     */
    Map<ExhibitionHall, Integer> getTopKHotExhibitionHall(Long museumId, int k);
}
