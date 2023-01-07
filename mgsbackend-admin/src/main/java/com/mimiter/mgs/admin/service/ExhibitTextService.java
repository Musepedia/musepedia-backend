package com.mimiter.mgs.admin.service;

import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.service.base.CrudService;
import com.mimiter.mgs.model.entity.ExhibitText;

/**
 * 展品文本服务
 */
public interface ExhibitTextService extends CrudService<ExhibitText> {

    /**
     *
     * @param req 更新展品文本请求
     * @return 是否更新成功
     */
    boolean updateExhibitText(UpdateExhibitTextReq req);

}
