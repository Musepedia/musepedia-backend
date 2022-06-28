package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
import cn.abstractmgs.core.service.RecommendQuestionService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("recommendQuestionService")
public class RecommendQuestionImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    private List<String> cachedQuestions;

    @Override
    public List<String> getRandomQuestions(int count) {
        if(count <= 0){
            return new ArrayList<>();
        }

        if(cachedQuestions == null){
            Page<RecommendQuestion> page = new Page<>();
            page.setSize(100);
            cachedQuestions = page(page)
                    .getRecords()
                    .stream()
                    .map(RecommendQuestion::getQuestionText)
                    .collect(Collectors.toList());
        }

        int size = cachedQuestions.size();
        if(count >= size){
            return cachedQuestions;
        }

        List<String> recommendQuestions = new ArrayList<>();
        HashSet<Integer> selectedQuestions = new HashSet<>();
        Random random = new Random();
        int max = Math.min(count, cachedQuestions.size());
        while(selectedQuestions.size() < max){
            int i = random.nextInt(cachedQuestions.size());
            if(selectedQuestions.add(i)){
                recommendQuestions.add(cachedQuestions.get(i));
            }
        }

        return recommendQuestions;
    }
}
