package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("recommendQuestionService")
public class RecommendQuestionImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    private List<String> cachedQuestions;

    @Resource
    private RedisUtil redisUtil;

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

    @Override
    public void updateQuestionFreqByText(String question) {
        baseMapper.updateQuestionFreqByText(question);
    }

    @Override
    public void insertQuestion(String questionText, int answerType, String answerText, Long exhibitId) {
        baseMapper.insertQuestion(questionText, answerType, answerText, exhibitId);
    }

    @Override
    public RecommendQuestion getRandomQuestionWithSameExhibitId(Long exhibitId) {
        return baseMapper.selectRandomQuestionWithSameExhibitId(exhibitId);
    }

    @Override
    public RecommendQuestion selectQuestionByText(String question) {
        return baseMapper.selectQuestionByText(question);
    }

    @Override
    public List<RecommendQuestion> selectMostFrequentQuestions(int count) {
        return baseMapper.selectMostFrequentQuestions(count);
    }

    @Override
    public RecommendQuestion getRecommendQuestion(String question) {
        RecommendQuestion cached = (RecommendQuestion) redisUtil.get(question);
        if (cached == null) {
            cached = selectQuestionByText(question);
            if (cached == null) {
                return null;
            }
            redisUtil.set(cached.getQuestionText(), cached);
        }

        return cached;
    }
}
