package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RecommendQuestionService extends IService<RecommendQuestion> {

    List<String> getRandomQuestions(int count);
}
