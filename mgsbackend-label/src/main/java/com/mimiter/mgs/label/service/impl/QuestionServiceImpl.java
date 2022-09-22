package com.mimiter.mgs.label.service.impl;

import com.mimiter.mgs.label.model.entity.Question;
import com.mimiter.mgs.label.repository.QuestionRepository;
import com.mimiter.mgs.label.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionRepository, Question> implements QuestionService {

    @Override
    public List<Question> selectByParagraphId(Long id) {
        return baseMapper.selectByParagraphId(id);
    }
}
