package com.mimiter.mgs.core.model.dto;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
