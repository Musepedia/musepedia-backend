package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.request.UpdateExhibitAliasReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.model.entity.ExhibitAlias;

/**
 * 展品别名服务
 */
public interface ExhibitAliasService extends CrudService<ExhibitAlias> {

    /**
     * 更新展品别名
     *
     * @param req 更新展品文本请求参数
     * @return 是否更新成功
     */
    boolean updateExhibitAlias(UpdateExhibitAliasReq req);
}
