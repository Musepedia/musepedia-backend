package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.request.UpdateMuseumReq;
import com.mimiter.mgs.model.entity.Museum;
import com.mimiter.mgs.admin.model.request.AddMuseumReq;
import com.mimiter.mgs.admin.model.request.LoginReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.common.exception.BadRequestException;

/**
 * 博物馆服务
 */
public interface AdminMuseumService extends CrudService<Museum> {

    /**
     * 添加博物馆
     *
     * @param req 添加博物馆请求
     * @return 添加的博物馆
     */
    Museum addMuseum(AddMuseumReq req);

    /**
     * 更新博物馆
     *
     * @param req 更新博物馆请求
     * @return 是否更新成功
     */
    boolean updateById(UpdateMuseumReq req);

    /**
     * 切换博物馆经营状态
     *
     * @param id 博物馆ID
     */
    void setServiced(Long id, boolean service);
}
