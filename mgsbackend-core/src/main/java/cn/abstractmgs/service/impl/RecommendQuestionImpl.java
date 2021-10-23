package cn.abstractmgs.service.impl;

import cn.abstractmgs.model.entity.RecommendQuestion;
import cn.abstractmgs.repository.RecommendQuestionRepository;
import cn.abstractmgs.service.RecommendQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("recommendQuestionServiceImpl")
public class RecommendQuestionImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    @Override
    public List<RecommendQuestion> selectByQuestionId(int id) {
        return baseMapper.selectByQuestionId(id);
    }

    @Override
    public int countTable() {
        return baseMapper.countTable();
    }

    @Override
    public List<String> getRandomQuestions(int count) {
        int maxCount = countTable();

        HashSet<Integer> randomIndex = new HashSet<>();
        List<String> recommendQuestions = new ArrayList<>();
        while (randomIndex.size() < count) {
            Random random = new Random();
            randomIndex.add(random.nextInt(maxCount)+1);
        }

        for (int index : randomIndex) {
            String question = selectByQuestionId(index).get(0).getQuestion();
            recommendQuestions.add(question);
        }

        return recommendQuestions;
    }
}
