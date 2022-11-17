package com.mimiter.mgs.label.service;

import com.mimiter.mgs.label.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface QuestionService extends IService<Question> {

    List<Question> selectByParagraphId(Long id);
}
