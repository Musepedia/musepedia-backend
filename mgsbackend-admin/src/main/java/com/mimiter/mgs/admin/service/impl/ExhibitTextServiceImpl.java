package com.mimiter.mgs.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mimiter.mgs.admin.model.request.UpdateExhibitTextReq;
import com.mimiter.mgs.admin.repository.ExhibitTextRepository;
import com.mimiter.mgs.admin.service.ExhibitTextService;
import com.mimiter.mgs.admin.service.base.AbstractCrudService;
import com.mimiter.mgs.model.entity.ExhibitText;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("exhibitTextService")
public class ExhibitTextServiceImpl extends
        AbstractCrudService<ExhibitTextRepository, ExhibitText> implements ExhibitTextService {

    @Override
    public boolean updateExhibitText(UpdateExhibitTextReq req) {
        Long exhibitId = req.getExhibitId();
        getBaseMapper().deleteByExhibitId(exhibitId);

        for (String text : req.getExhibitText()) {
            ExhibitText exhibitText = new ExhibitText();
            exhibitText.setExhibitId(exhibitId);
            exhibitText.setText(text);
            save(exhibitText);
        }

        return true;
    }

    @Override
    public List<String> listExhibitText(Long exhibitId) {
        ArrayList<String> textList = new ArrayList<>();
        QueryWrapper<ExhibitText> et = new QueryWrapper<>();
        et.eq("exhibit_id", exhibitId);

        for (ExhibitText exhibitText : list(et)) {
            String alias = exhibitText.getText();
            textList.add(alias);
        }
        return textList;
    }
}
