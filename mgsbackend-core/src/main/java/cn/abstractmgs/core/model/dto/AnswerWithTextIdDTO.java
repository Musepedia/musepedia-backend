package cn.abstractmgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerWithTextIdDTO {

    private Long questionId;

    private String answer;

    private Long textId;
}
