package cn.abstractmgs.core.service.impl;

<<<<<<< HEAD
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
=======
import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
import cn.abstractmgs.core.service.ExhibitService;
>>>>>>> origin/beta0.2.5-re
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
<<<<<<< HEAD
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
=======
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
>>>>>>> origin/beta0.2.5-re
import java.util.stream.Collectors;

@Service("recommendQuestionService")
public class RecommendQuestionImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    private List<String> cachedQuestions;

    @Resource
<<<<<<< HEAD
=======
    private ExhibitService exhibitService;

    @Resource
    private RecommendQuestionService recommendQuestionService;


    @Resource
>>>>>>> origin/beta0.2.5-re
    private RedisUtil redisUtil;

    @Override
    public List<String> getRandomQuestions(int count) {
<<<<<<< HEAD
        if(count <= 0){
            return new ArrayList<>();
        }

        if(cachedQuestions == null){
=======
        if (count <= 0) {
            return new ArrayList<>();
        }

        if (cachedQuestions == null) {
>>>>>>> origin/beta0.2.5-re
            Page<RecommendQuestion> page = new Page<>();
            page.setSize(100);
            cachedQuestions = page(page)
                    .getRecords()
                    .stream()
                    .map(RecommendQuestion::getQuestionText)
                    .collect(Collectors.toList());
        }

        int size = cachedQuestions.size();
<<<<<<< HEAD
        if(count >= size){
=======
        if (count >= size) {
>>>>>>> origin/beta0.2.5-re
            return cachedQuestions;
        }

        List<String> recommendQuestions = new ArrayList<>();
        HashSet<Integer> selectedQuestions = new HashSet<>();
        Random random = new Random();
        int max = Math.min(count, cachedQuestions.size());
<<<<<<< HEAD
        while(selectedQuestions.size() < max){
            int i = random.nextInt(cachedQuestions.size());
            if(selectedQuestions.add(i)){
=======
        while (selectedQuestions.size() < max) {
            int i = random.nextInt(cachedQuestions.size());
            if (selectedQuestions.add(i)) {
>>>>>>> origin/beta0.2.5-re
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
<<<<<<< HEAD
=======

    private long[] findNearest(long id, List<Long> allId) {
        long min = id;
        long[] re = {0, 0};
        int index = 0;
        for (int i = 0; i < allId.size(); ++i) {
            if (Math.abs(allId.get(i) - id) < min) {
                min = Math.abs(allId.get(i) - id);
                index = i;
            }
        }
        re[0] = allId.get(index);
        allId.remove(index);
        min = id;
        for (int i = 0; i < allId.size(); ++i) {
            if (Math.abs(allId.get(i) - id) < min) {
                min = Math.abs(allId.get(i) - id);
                index = i;
            }
        }
        re[1] = allId.get(index);
        return re;
    }

    @Override
    public List<String> selectRecommendQuestions(String originalQ, String originalAnswer, long Id) {
        List<String> RecommendQuestions = new ArrayList<>();

        //judge answerable
        int reMode = 0;
        if (!originalAnswer.isEmpty()) {//can answer
            reMode = 1;
        } else {
            if (Id != -1) {//can't answer, can locate
                reMode = 3;
            } else {//random, can't locate
                reMode = 2;
            }
        }

        //do Recommendation
        switch (reMode) {
            case 1: {
                List<Exhibit> nowHall = new ArrayList<Exhibit>();
                List<Long> idINHall = new ArrayList<Long>();
                nowHall = exhibitService.getExhibitsInSameExhibitionHall(Id);
                for (int i = 0; i < nowHall.size(); ++i) {
                    Exhibit temp = nowHall.get(i);
                    long a = temp.getId();
                    idINHall.add(a);
                }
                long[] near = findNearest(Id, idINHall);
                RecommendQuestions.add(recommendQuestionService.getRandomQuestionWithSameExhibitId(near[0]).getQuestionText());
                RecommendQuestions.add(recommendQuestionService.getRandomQuestionWithSameExhibitId(near[1]).getQuestionText());

                break;
            }
            case 2: {
                RecommendQuestions = recommendQuestionService.getRandomQuestions(3);
                break;
            }
            case 3: {
                List<Exhibit> nowHall = new ArrayList<Exhibit>();
                List<Long> idINHall = new ArrayList<Long>();
                nowHall = exhibitService.getExhibitsInSameExhibitionHall(Id);
                for (int i = 0; i < nowHall.size(); ++i) {
                    Exhibit temp = nowHall.get(i);
                    long a = temp.getId();
                    idINHall.add(a);
                }
                long[] near = findNearest(Id, idINHall);
                RecommendQuestions.add(recommendQuestionService.getRandomQuestionWithSameExhibitId(near[0]).getQuestionText());
                RecommendQuestions.add(recommendQuestionService.getRandomQuestionWithSameExhibitId(near[1]).getQuestionText());

                //查询当前展品的其他问题
                String nowQuestion = recommendQuestionService.getRandomQuestionWithSameExhibitId(Id + 1).getQuestionText();
                RecommendQuestions.add(nowQuestion);
                break;
            }
        }


        return RecommendQuestions;
    }
>>>>>>> origin/beta0.2.5-re
}
