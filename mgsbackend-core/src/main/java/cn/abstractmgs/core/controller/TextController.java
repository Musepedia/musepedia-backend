package cn.abstractmgs.core.controller;

import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.service.ExhibitTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/select")
public class TextController {
    /*
     * 仅测试用
     */

    @Autowired
    private ExhibitTextService service;

    @Autowired
    private RecommendQuestionService recommendQuestionService;

    @GetMapping("text")
    public String selectText(@RequestParam(value = "question") String question) {
        return service.getText(question);
    }

    @GetMapping("question")
    public List<String> selectQuestion() {
        return recommendQuestionService.getRandomQuestions(2);
    }
}