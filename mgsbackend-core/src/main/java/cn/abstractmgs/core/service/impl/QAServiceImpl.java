package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.HelloRequest;
import cn.abstractmgs.core.MyServiceGrpc;
import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.model.entity.RecommendQuestion;
import cn.abstractmgs.core.service.ExhibitTextService;
import cn.abstractmgs.core.service.QAService;
import cn.abstractmgs.core.service.RecommendQuestionService;
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

    @GrpcClient("myService")
    private MyServiceGrpc.MyServiceBlockingStub myServiceBlockingStub;

    public static final Pattern placeholderPattern = Pattern.compile("[\\[A-Z\\]]");

    private static final String DEFAULT_ANSWER = "暂时无法回答这个问题";

    @Override
    public int getStatus(String answer) {
        return answer.equals(DEFAULT_ANSWER) ? 0 : 1;
    }

    @Override
    public String getAnswer(String question) {
        // 尝试从缓存中和数据库中获取答案
        RecommendQuestion recommendQuestion = recommendQuestionService.getRecommendQuestion(question);

        if (recommendQuestion != null) {
            // 在数据库中更新question_freq
            recommendQuestionService.updateQuestionFreqByText(question);
            switch (recommendQuestion.getAnswerType()) {
                case 1:
                    return recommendQuestion.getAnswerText();
                case 2:
                    // todo 对应生成的图片作为静态资源返回
                    return null;
                default:
                    // 包括answer_type = 0时的情况
                    return DEFAULT_ANSWER;
            }
        }

        // 无法从缓存或数据库中找到答案，需要经过Python模型抽取文本
        List<ExhibitText> exhibitTexts = exhibitTextService.getAllTexts(question);
        List<String> texts = new ArrayList<>();
        for (ExhibitText exhibitText : exhibitTexts) {
            texts.add(exhibitText.getText());
        }

        String answer = DEFAULT_ANSWER;
        if (texts.size() != 0) {
            HelloRequest helloRequest = HelloRequest
                    .newBuilder()
                    .setQuestion(question)
                    .addAllTexts(texts)
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

        // 将答案写入数据库中
        recommendQuestionService.insertQuestion(question,
                                                getStatus(answer),
                                                getStatus(answer) == 0 ? null : answer,
                                                exhibitTexts.get(0).getExhibitId());

        return answer;
    }
}
