package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.request.UpsertExhibitReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.model.entity.Exhibit;

/**
 * 展品服务
 */
public interface ExhibitService extends CrudService<Exhibit> {

    /**
     * @param req 添加展品请求
     * @return 添加的展品
     */
    Exhibit addExhibit(UpsertExhibitReq req);

    /**
     *
     * @param req 更新展品请求
     * @return 是否更新成功
     */
    boolean updateExhibit(UpsertExhibitReq req);
}
