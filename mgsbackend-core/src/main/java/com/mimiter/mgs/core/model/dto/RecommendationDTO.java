package com.mimiter.mgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 提问时后端推荐的问题
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private Long questionId;

    private String questionText;

    private int answerType;

    private String answerText;

    private String exhibitFigureUrl;
}
