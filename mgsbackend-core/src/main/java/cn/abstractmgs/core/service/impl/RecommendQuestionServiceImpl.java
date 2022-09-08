package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.model.entity.Exhibit;
import cn.abstractmgs.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
import cn.abstractmgs.core.service.ExhibitService;
import cn.abstractmgs.core.service.ExhibitTextService;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.common.utils.RedisUtil;
import cn.abstractmgs.core.utils.ThreadContextHolder;
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
    public void insertQuestion(String questionText, int answerType, String answerText, Long exhibitId, Long exhibitTextId, Long museumId) {
        baseMapper.insertQuestion(questionText, answerType, answerText, exhibitId, exhibitTextId, museumId);
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

        try {
            for (int i = 0; i < allId.size(); ++i) {
                re[i] = allId.get(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }

    private List<String> preSelect(List<Long> idNear) {
        long[] near = findNearest(idNear);
        List<String> recommendQuestions = new ArrayList<>();
        if (near[0] == 0 && near[1] == 0) {//only on exhibit in local hall
            recommendQuestions = getRandomQuestions(3, ThreadContextHolder.getCurrentMuseumId());
        } else {
            for (int i = 0; i < 2; ++i) {
                if (near[i] != 0) {
                    try {
                        recommendQuestions.add(getRandomQuestionWithSameExhibitId(near[i]).getQuestionText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return recommendQuestions;
    }

    @Override
    public List<String> selectRecommendQuestions(String originalQuestion, String originalAnswer, Long museumId) {
        List<String> recommendQuestions = new ArrayList<>();

        // TODO 由answer -> ID
        List<String> labels = exhibitTextService.getLabel(originalQuestion, museumId);
        Long exhibitId = null;
        if (labels != null && !labels.isEmpty()) {
            exhibitId = exhibitService.selectExhibitIdByLabel(labels.get(0));
        }


        RecommendQuestion answerInfo = this.selectQuestionByText(originalQuestion, museumId);
        int answerType = answerInfo.getAnswerType();

        //judge answerable
        int reMode;
        if (answerType != 0) {//can answer
            reMode = 1;
        } else {
            if (exhibitId == null) {//random, can't locate
                reMode = 2;
            } else {//can't answer, can locate
                reMode = 3;
            }
        }

        //do Recommendation
        switch (reMode) {
        //can answer and recommend
        case 1: {
            List<Exhibit> nowHall;

            List<Long> idNear = new ArrayList<>();
            nowHall = exhibitService.selectPreviousAndNextExhibitById(exhibitId);
            for (Exhibit temp : nowHall) {
                long a = temp.getId();
                idNear.add(a);
            }
            recommendQuestions = preSelect(idNear);
            break;
        }
        //no exhibit and random recommend
        case 2: {
            return getRandomQuestions(3, ThreadContextHolder.getCurrentMuseumId());
        }
        //can answer the question, so recommend another question about local question
        case 3: {
            List<Exhibit> nowHall;

            List<Long> idNear = new ArrayList<>();
            nowHall = exhibitService.selectPreviousAndNextExhibitById(exhibitId);
            for (Exhibit temp : nowHall) {
                long a = temp.getId();
                idNear.add(a);
            }

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
