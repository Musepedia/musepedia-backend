package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.repository.RecommendQuestionRepository;
import cn.abstractmgs.core.service.ExhibitService;
import cn.abstractmgs.core.service.ExhibitTextService;
import cn.abstractmgs.core.service.RecommendQuestionService;
import cn.abstractmgs.core.utils.RedisUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service("recommendQuestionService")
public class RecommendQuestionImpl extends ServiceImpl<RecommendQuestionRepository, RecommendQuestion> implements RecommendQuestionService {

    private List<String> cachedQuestions;

    @Resource
    private ExhibitService exhibitService;

    @Resource
    private RecommendQuestionService recommendQuestionService;

    @Resource
    private ExhibitTextService exhibitTextService;


    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<String> getRandomQuestions(int count) {
        if (count <= 0) {
            return new ArrayList<>();
        }

        if (cachedQuestions == null) {
            Page<RecommendQuestion> page = new Page<>();
            page.setSize(100);
            cachedQuestions = page(page)
                    .getRecords()
                    .stream()
                    .map(RecommendQuestion::getQuestionText)
                    .collect(Collectors.toList());
        }

        int size = cachedQuestions.size();
        if (count >= size) {
            return cachedQuestions;
        }

        List<String> recommendQuestions = new ArrayList<>();
        HashSet<Integer> selectedQuestions = new HashSet<>();
        Random random = new Random();
        int max = Math.min(count, cachedQuestions.size());
        while (selectedQuestions.size() < max) {
            int i = random.nextInt(cachedQuestions.size());
            if (selectedQuestions.add(i)) {
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
    public void insertQuestion(String questionText, int answerType, String answerText, Long exhibitId, Long exhibitTextId) {
        baseMapper.insertQuestion(questionText, answerType, answerText, exhibitId, exhibitTextId);
    }

    @Override
    public void insertIrrelevantQuestion(String questionText) {
        baseMapper.insertQuestion(questionText, 0, null, null, null);
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
        // table:primary_key:field
        RecommendQuestion cached = (RecommendQuestion) redisUtil.get(redisUtil.getKey("question", question, "answer"));
        if (cached == null) {
            cached = selectQuestionByText(question);
            if (cached == null) {
                return null;
            }
            redisUtil.set(redisUtil.getKey("question", cached.getQuestionText(), "answer"), cached);
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

    private List<String> preSelect(List<Long> idNear){
        long[] near = findNearest(idNear);
        List<String> recommendQuestions=new ArrayList<>();
        if (near[0] == 0 && near[1] == 0) {//only on exhibit in local hall
            recommendQuestions = recommendQuestionService.getRandomQuestions(3);
        } else {
            for (int i = 0; i < 2; ++i) {
                if (near[i] != 0) {
                    try {
                        recommendQuestions.add(recommendQuestionService.getRandomQuestionWithSameExhibitId(near[i]).getQuestionText());
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

        List<String> RecommendQuestions = new ArrayList<>();

        // TODO 由answer -> ID
        List<String> labels = exhibitTextService.getLabel(originalQuestion, museumId);
        Long id = exhibitService.selectExhibitIdByLabel(labels.get(0));

        RecommendQuestion answerInfo = this.selectQuestionByText(originalQuestion);
        int answerType = answerInfo.getAnswerType();

        //judge answerable
        int reMode;
        if (answerType != 0) {//can answer
            reMode = 1;
        } else {
            if (id == 0) {//random, can't locate
                reMode = 2;
            } else {//can't answer, can locate
                reMode = 3;
            }
        }

        //do Recommendation
        switch (reMode) {
            //can answer and recommend
            case 1: {
                List<Exhibit> nowHall ;

                List<Long> idNear = new ArrayList<>();
                nowHall = exhibitService.selectPreviousAndNextExhibitById(id);
                for (Exhibit temp : nowHall) {
                    long a = temp.getId();
                    System.out.println(a);
                    idNear.add(a);
                }
                RecommendQuestions=preSelect(idNear);
                break;
            }
            //no exhibit and random recommend
            case 2: {
                System.out.println("this is random");
                RecommendQuestions = recommendQuestionService.getRandomQuestions(3);
                break;
            }
            //can answer the question, so recommend another question about local question
            case 3: {
                List<Exhibit> nowHall ;

                List<Long> idNear = new ArrayList<>();
                nowHall = exhibitService.selectPreviousAndNextExhibitById(id);
                for (Exhibit temp : nowHall) {
                    long a = temp.getId();
                    idNear.add(a);
                }
                RecommendQuestions=preSelect(idNear);
                //查询当前展品的其他问题
                String nowQuestion = recommendQuestionService.getRandomQuestionWithSameExhibitId(id).getQuestionText();
                RecommendQuestions.add(nowQuestion);
                break;
            }
        }
        return RecommendQuestions;
    }
}
