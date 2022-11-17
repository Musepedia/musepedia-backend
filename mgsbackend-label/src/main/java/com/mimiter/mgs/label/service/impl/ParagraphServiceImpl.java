package com.mimiter.mgs.label.service.impl;

import com.mimiter.mgs.label.model.entity.Paragraph;
import com.mimiter.mgs.label.repository.ParagraphRepository;
import com.mimiter.mgs.label.service.ParagraphService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("paragraphService")
public class ParagraphServiceImpl extends ServiceImpl<ParagraphRepository, Paragraph> implements ParagraphService {
}
