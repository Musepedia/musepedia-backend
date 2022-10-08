package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.core.repository.ExhibitTextRepository;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.utils.NLPUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("exhibitTextService")
public class ExhibitTextServiceImpl extends ServiceImpl<ExhibitTextRepository, ExhibitText> implements ExhibitTextService {

    public List<String> getLabel(List<String> labels, String question) {
        NLPUtil nlpUtil = new NLPUtil(question);
        nlpUtil.updateCustomDictionary(labels);

        return nlpUtil.getNoun();
    }

    @Override
    public List<String> getLabel(String question, Long museumId) {
        List<String> storedLabels = selectAllLabelsWithAliases(museumId);
        NLPUtil nlpUtil = new NLPUtil(question);
        nlpUtil.updateCustomDictionary(storedLabels);

        return nlpUtil.getNoun();
    }

    @Override
    public List<ExhibitText> selectByLabel(List<String> labels, Long museumId) {
        return baseMapper.selectByLabel(labels, museumId);
    }

    @Override
    public List<String> selectAllLabelsWithAliases(Long museumId) {
        return baseMapper.selectAllLabelsWithAliases(museumId);
    }

    @Override
    public List<ExhibitText> getAllTexts(String question, Long museumId) {
        List<String> storedLabels = selectAllLabelsWithAliases(museumId);

        List<String> labels = getLabel(storedLabels, question);
        List<ExhibitText> exhibitTexts = selectByLabel(labels, museumId);

        return exhibitTexts.size() >= MAX_TEXTS_COUNT
                ? new ArrayList<>()
                : exhibitTexts;
    }
}
