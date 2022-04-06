package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.HelloRequest;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.*;
import cn.abstractmgs.core.utils.NLPUtil;
import cn.abstractmgs.core.utils.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service("qaService")
public class QAServiceImpl implements QAService {

    @Resource
    private RecommendQuestionService recommendQuestionService;

    @Resource
    private ExhibitTextService exhibitTextService;

    @Resource
    private ExhibitService exhibitService;

    @Resource
    private UserService userService;

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public static final Pattern placeholderPattern = Pattern.compile("[\\[A-Z\\]]");

    public static final Pattern urlPattern = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)\n");

    private static final String DEFAULT_ANSWER = "暂时无法回答这个问题";

    @Override
    public int getStatus(String answer) {
        if (answer.equals(DEFAULT_ANSWER)) {
            return 0;
        } else if (urlPattern.matcher(answer).matches()) {
            return 2;
        } else {
            return 1;
        }
    }

    @Override
    public String getAnswer(String question) {
        // 尝试从缓存中和数据库中获取答案
        RecommendQuestion recommendQuestion = recommendQuestionService.getRecommendQuestion(question);

        Long userLocation;

        if (recommendQuestion != null) {
            // 在数据库中更新question_freq
            recommendQuestionService.updateQuestionFreqByText(question);

            // 根据问题更新用户所在位置
            userLocation = exhibitService.selectExhibitionHallIdByExhibitId(recommendQuestion.getExhibitId());
            userService.setUserLocation(SecurityUtil.getCurrentUserId(), userLocation);

            return recommendQuestion.getAnswerType() == 0 ? DEFAULT_ANSWER : recommendQuestion.getAnswerText();
        }

        // 回答类型识别与关键词分析，当回答类型=3时直接返回展品对应的图片，其余情况在分词后获取对应的text
        NLPUtil nlpUtil = new NLPUtil(question);

        int answerType = nlpUtil.answerRecognition(question);

        List<String> label = exhibitTextService.getLabel(exhibitTextService.selectAllLabelsWithAliases(), question);

        // 无法从缓存或数据库中找到答案，需要经过Python模型抽取文本
        List<ExhibitText> exhibitTexts = exhibitTextService.getAllTexts(question);
        List<String> texts = new ArrayList<>();
        for (ExhibitText exhibitText : exhibitTexts) {
            texts.add(exhibitText.getText());
        }


        if (answerType == 3) {
            recommendQuestionService.insertQuestion(question, 3, null, exhibitTexts.get(0).getExhibitId());
            return exhibitService.selectExhibitFigureUrlByLabel(label.get(0));
        }

        String answer = DEFAULT_ANSWER;
        if (texts.size() != 0) {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(texts)
                    .setStatus(answerType)
                    .build();

            try {
                String resp = myServiceBlockingStub
                        .sayHello(helloRequest)
                        .getAnswer();
                // 替换grpc返回的所有包含[CLS]的占位符，如果仅包含占位符则返回"无法回答"
                resp = placeholderPattern.matcher(resp).replaceAll("");
                if(resp.length() != 0){
                    // has exact answer
                    answer = resp;
                }
            } catch (Exception e){
                // rpc error
                log.error("Rpc error: ",e);
            }
        }

        // 根据问题更新用户所在位置
        userLocation = exhibitService.selectExhibitionHallIdByExhibitId(exhibitTexts.get(0).getExhibitId());
        userService.setUserLocation(SecurityUtil.getCurrentUserId(), userLocation);

        // 将答案写入数据库中
        recommendQuestionService.insertQuestion(question,
                                                getStatus(answer),
                                                getStatus(answer) == 0 ? null : answer,
                                                exhibitTexts.get(0).getExhibitId());

        return answer;
    }
}
