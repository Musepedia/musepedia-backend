package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.ExhibitText;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 展品文本服务
 */
public interface ExhibitTextService extends IService<ExhibitText> {

    int MAX_TEXTS_COUNT = 15;  // 允许至多抽取的text数量

    List<ExhibitText> selectByLabel(List<String> labels, Long museumId);

    List<ExhibitText> getAllTexts(String question, Long museumId);

    List<String> selectAllLabelsWithAliases(Long museumId);

    List<String> getLabel(List<String> labels, String question);

    List<String> getLabel(String question, Long museumId);

    List<ExhibitText> getAllTextsForLucene();

    List<String> getTextsByExhibitId(Long exhibitId);
}
