package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.dto.AnswerWithTextIdDTO;

public interface QAService {

    int getStatus(String answer);

    AnswerWithTextIdDTO getAnswer(String question, Long museumId);
}
