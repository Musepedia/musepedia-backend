package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.dto.ExhibitDTO;
import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.model.entity.Exhibit;

/**
 * 展品服务
 */
public interface ExhibitService extends CrudService<Exhibit> {

    /**
     * 添加展品
     *
     * @param req 添加展品请求
     * @return 添加的展品
     */
    Exhibit addExhibit(UpsertExhibitReq req);

    /**
     * 更新展品
     *
     * @param req 更新展品请求
     * @return 是否更新成功
     */
    boolean updateExhibit(UpsertExhibitReq req);

    /**
     * 转换为ExhibitDTO
     *
     * @param exhibit /
     * @return null如果exhibit是null，否则返回转换后的ExhibitDTO
     */
    ExhibitDTO toDTO(Exhibit exhibit);
}
