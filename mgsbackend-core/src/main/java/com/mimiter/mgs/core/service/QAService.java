package com.mimiter.mgs.core.service;

import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;

/**
 * 问答服务
 */
public interface QAService {

    AnswerWithTextIdDTO getAnswer(String question, Long museumId);
}
