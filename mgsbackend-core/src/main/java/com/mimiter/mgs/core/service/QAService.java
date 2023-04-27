package com.mimiter.mgs.core.service;

import com.mimiter.mgs.core.model.dto.AnswerWithTextIdDTO;
import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * 问答服务
 */
public interface QAService {

    AnswerWithTextIdDTO getAnswer(String question, Long museumId) throws IOException, ParseException;
}
