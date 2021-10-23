package cn.abstractmgs.service;

import cn.abstractmgs.model.entity.RecommendQuestion;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecommendQuestionService extends IService<RecommendQuestion> {
    List<RecommendQuestion> selectByQuestionId(@Param("id") int id);
    int countTable();
    List<String> getRandomQuestions(int count);
}
