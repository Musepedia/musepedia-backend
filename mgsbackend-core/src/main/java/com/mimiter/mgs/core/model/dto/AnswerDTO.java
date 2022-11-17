package com.mimiter.mgs.core.model.dto;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * QA回答DTO，包含推荐问题，可能的推荐展区等，用于QA页面。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    private Integer status;

    private Long questionId;

    private String answer;

    private Long textId;

    private List<String> recommendQuestions;

    private ExhibitionHall recommendExhibitionHall;
}
