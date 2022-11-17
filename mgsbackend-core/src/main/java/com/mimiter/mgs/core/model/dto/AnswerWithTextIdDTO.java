package com.mimiter.mgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QA回答DTO，{@link com.mimiter.mgs.core.service.QAService}返回用。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerWithTextIdDTO {

    private Long questionId;

    private String answer;

    private int answerType;

    private Long textId;
}
