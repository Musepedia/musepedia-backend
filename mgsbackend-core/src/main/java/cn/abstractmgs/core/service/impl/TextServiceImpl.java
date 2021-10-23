package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Text;
import cn.abstractmgs.core.repository.TextRepository;
import cn.abstractmgs.core.service.TextService;
import cn.abstractmgs.core.utils.NLPTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("textServiceImpl")
public class TextServiceImpl extends ServiceImpl<TextRepository, Text> implements TextService {

    private List<String> getLabel(String question) {
        NLPTool nlpTool = new NLPTool(question);
        return nlpTool.getNoun();
    }

    @Override
    public List<Text> selectByLabel(String label) {
        return baseMapper.selectByLabel(label);
    }

    public String getText(String question) {
        List<String> labels = getLabel(question);

        ArrayList<Text> texts = new ArrayList<>();
        for (String label : labels) {
            List<Text> possibleTexts = selectByLabel(label);
            if (!possibleTexts.isEmpty()){
                texts.add(possibleTexts.get(0));
            }
        }

        return texts.size() != 1
                ? null
                : texts.get(0).getText();
    }
}
