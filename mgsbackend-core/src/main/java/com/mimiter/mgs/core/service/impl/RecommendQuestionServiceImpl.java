package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.Exhibit;
import com.mimiter.mgs.model.entity.RecommendQuestion;
import com.mimiter.mgs.core.repository.RecommendQuestionRepository;
import com.mimiter.mgs.core.service.ExhibitService;
import com.mimiter.mgs.core.service.ExhibitTextService;
import com.mimiter.mgs.core.service.RecommendQuestionService;
import com.mimiter.mgs.common.utils.RedisUtil;
import com.mimiter.mgs.core.utils.ThreadContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("recommendQuestionService")
public class RecommendQuestionServiceImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    /**
     * MuseumId到问题的映射
     */
    private final Map<Long, List<String>> cachedQuestions = new ConcurrentHashMap<>();

    private final ExhibitService exhibitService;

    private final ExhibitTextService exhibitTextService;

    private final RedisUtil redisUtil;

    @Override
    public List<String> getRandomQuestions(int count, Long museumId) {
        if (count <= 0) {
            return new ArrayList<>();
        }

        List<String> cachedMuseumQuestions = cachedQuestions.get(museumId);
        if (cachedMuseumQuestions == null) {
            QueryWrapper<RecommendQuestion> wrapper = new QueryWrapper<>();
            wrapper.eq("museum_id", museumId);
            wrapper.ne("answer_type", 0);
            Page<RecommendQuestion> page = new Page<>();
            page.setSize(100);

            cachedMuseumQuestions = page(page, wrapper)
                    .getRecords()
                    .stream()
                    .map(RecommendQuestion::getQuestionText)
                    .collect(Collectors.toList());
            cachedQuestions.put(museumId, cachedMuseumQuestions);
        }

        int size = cachedMuseumQuestions.size();
        if (count >= size) {
            return cachedMuseumQuestions;
        }

        List<String> recommendQuestions = new ArrayList<>();
        HashSet<Integer> selectedQuestions = new HashSet<>();
        Random random = new Random();
        int max = Math.min(count, cachedMuseumQuestions.size());
        while (selectedQuestions.size() < max) {
            int i = random.nextInt(cachedMuseumQuestions.size());
            if (selectedQuestions.add(i)) {
                recommendQuestions.add(cachedMuseumQuestions.get(i));
            }
        }

        return recommendQuestions;
    }

    @Override
    public void updateQuestionFreqByText(String question, Long museumId) {
        baseMapper.updateQuestionFreqByText(question, museumId);
    }

    @Override
    public void insertIrrelevantQuestion(String questionText, Long museumId) {
        baseMapper.insertQuestion(questionText, 0, null, null, null, museumId);
    }

    @Override
    public RecommendQuestion getRandomQuestionWithSameExhibitId(Long exhibitId) {
        return baseMapper.selectRandomQuestionWithSameExhibitId(exhibitId);
    }

    @Override
    public RecommendQuestion selectQuestionByText(String question, Long museumId) {
        return baseMapper.selectQuestionByText(question, museumId);
    }

    @Override
    public List<RecommendQuestion> selectMostFrequentQuestions(int count, Long exhibitId) {
        return baseMapper.selectMostFrequentQuestions(count, exhibitId);
    }

    @Override
    public RecommendQuestion getRecommendQuestion(String question, Long museumId) {
        // table:primary_key:field
        // question:xxx:museumId:xx:answer
        RecommendQuestion cached = (RecommendQuestion) redisUtil.get(
                redisUtil.getKey("question", question, "museumId", museumId, "answer"));
        if (cached == null) {
            cached = selectQuestionByText(question, museumId);
            if (cached == null) {
                return null;
            }
            redisUtil.set(
                    redisUtil.getKey("question", cached.getQuestionText(), "museumId", museumId, "answer"),
                    cached, 1, TimeUnit.DAYS);
        }

        return cached;
    }


    private long[] findNearest(List<Long> allId) {
        long[] re = {0, 0};
        for (int i = 0; i < re.length && i < allId.size(); ++i) {
            re[i] = allId.get(i);
        }
        return re;
    }

    private List<String> preSelect(List<Long> idNear) {
        long[] near = findNearest(idNear);
        if (near[0] == 0 && near[1] == 0) {//only on exhibit in local hall
            return getRandomQuestions(3, ThreadContextHolder.getCurrentMuseumId());
        }

        List<String> recommendQuestions = new ArrayList<>();
        for (long l : near) {
            if (l != 0) {
                RecommendQuestion question = getRandomQuestionWithSameExhibitId(l);
                if(question != null){
                    recommendQuestions.add(question.getQuestionText());
                }
            }
        }
        return recommendQuestions;
    }

    private static final int CAN_ANSWER = 1;

    private static final int NO_EXHIBIT = 2;

    private static final int CANT_ANSWER_BUT_EXHIBIT_EXISTS = 3;

    @Override
    public List<String> selectRecommendQuestions(String originalQuestion, Long museumId) {
        List<String> recommendQuestions = new ArrayList<>();

        // TODO 由answer -> ID
        List<String> labels = exhibitTextService.getLabel(originalQuestion, museumId);
        Long exhibitId = null;
        if (labels != null && !labels.isEmpty()) {
            exhibitId = exhibitService.selectExhibitIdByLabelAndMuseumId(labels.get(0), museumId);
        }


        RecommendQuestion answerInfo = this.selectQuestionByText(originalQuestion, museumId);
        int answerType = answerInfo.getAnswerType();

        //judge answerable
        int reMode;
        if (answerType != QAServiceImpl.TYPE_DEFAULT_ANSWER) { //can answer
            reMode = CAN_ANSWER;
        } else if (exhibitId == null) { //random, can't locate
            reMode = NO_EXHIBIT;
        } else {//can't answer, can locate
            reMode = CANT_ANSWER_BUT_EXHIBIT_EXISTS;
        }

        //do Recommendation
        switch (reMode) {
        //can answer and recommend
        case CAN_ANSWER: {
            List<Long> idNear = exhibitService.selectPreviousAndNextExhibitById(exhibitId)
                    .stream().map(Exhibit::getId).collect(Collectors.toList());
            return preSelect(idNear);
        }
        //no exhibit and random recommend
        case NO_EXHIBIT: {
            return getRandomQuestions(3, ThreadContextHolder.getCurrentMuseumId());
        }
        //can answer the question, so recommend another question about local question
        case CANT_ANSWER_BUT_EXHIBIT_EXISTS: {
            List<Long> idNear = exhibitService.selectPreviousAndNextExhibitById(exhibitId)
                    .stream().map(Exhibit::getId).collect(Collectors.toList());
            recommendQuestions = preSelect(idNear);
            //查询当前展品的其他问题
            RecommendQuestion question = getRandomQuestionWithSameExhibitId(exhibitId);
            if (question != null) {
                recommendQuestions.add(question.getQuestionText());
            }
            break;
        }
        }
        return recommendQuestions;
    }

    @Override
    public List<RecommendQuestion> selectNonDislikeUserQuestion(Long userId, Long museumId) {
        return baseMapper.selectNonDislikeUserQuestion(userId, museumId);
    }


    @Override
    public List<RecommendQuestion> selectQuestionsWithFreqAndLabels(Long museumId) {
        return baseMapper.selectQuestionsWithFreqAndLabels(museumId);
    }

    @Override
    public List<RecommendQuestion> selectUserQuestionsWithLabels(Long museumId) {
        return baseMapper.selectUserQuestionsWithLabels(museumId);
    }
}
