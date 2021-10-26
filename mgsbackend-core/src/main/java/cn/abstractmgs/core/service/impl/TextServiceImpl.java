package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Text;
import cn.abstractmgs.core.repository.TextRepository;
import cn.abstractmgs.core.service.TextService;
import cn.abstractmgs.core.utils.NLPTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("textServiceImpl")
public class TextServiceImpl extends ServiceImpl<TextRepository, Text> implements TextService {

    private List<String> getLabel(String question) {
        NLPTool nlpTool = new NLPTool(question);
        return nlpTool.getNoun();
    }

    @Override
    public String modifyAnswer(String answer) {
        final String FAILURE_ANSWER = "暂时无法回答这个问题";

        if (answer == null)
            return FAILURE_ANSWER;
        String regex = "[\\[A-Z\\]]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(answer);

        String modifiedAnswer = matcher.replaceAll("");
        return modifiedAnswer.length() == 0
                ? FAILURE_ANSWER
                : modifiedAnswer;
    }

    @Override
    public List<String> selectByLabel(String label) {
        return baseMapper.selectByLabel(label);
    }

    @Override
    public String getText(String question) {
        List<String> labels = getLabel(question);

        ArrayList<String> texts = new ArrayList<>();
        for (String label : labels) {
            List<String> possibleTexts = selectByLabel(label);
            if (!possibleTexts.isEmpty()){
                texts.add(possibleTexts.get(0));
            }
        }

        return texts.size() != 1
                ? null
                : texts.get(0);
    }
}
