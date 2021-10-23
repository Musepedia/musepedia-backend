package cn.abstractmgs.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    private Integer status;

    private String answer;

    private List<String> recommendQuestions;
}
