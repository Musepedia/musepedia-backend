package cn.abstractmgs.model.entity;

import lombok.Data;

import java.util.List;

@Data
public class Answer {
    private int status;
    private String answer;
    private List<String> recommendQuestions;

    public Answer(int status, String answer, List<String> questions) {
        this.status = status;
        this.answer = answer;
        this.recommendQuestions = questions;
    }
}
