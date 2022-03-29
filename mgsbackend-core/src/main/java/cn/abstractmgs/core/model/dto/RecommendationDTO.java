package cn.abstractmgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private String questionText;

    private int answerType;

    private String answerText;

    private String exhibitFigureUrl;
}
