package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.repository.ExhibitTextRepository;
import cn.abstractmgs.core.service.TextService;
import cn.abstractmgs.core.utils.NLPTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("textServiceImpl")
public class TextServiceImpl extends ServiceImpl<ExhibitTextRepository, ExhibitText> implements TextService {

    private List<String> getLabel(List<String> labels, String question) {
        NLPTool nlpTool = new NLPTool(question);
        nlpTool.updateCustomDictionary(labels);

        return nlpTool.getNoun();
    }

    @Override
    public List<String> selectByLabel(String label) {
        return baseMapper.selectByLabel(label);
    }

    @Override
    public List<String> selectAllLabels() {
        return baseMapper.selectAllLabels();
    }

    @Override
    public String getText(String question) {
        List<String> storedLabels = selectAllLabels();

        List<String> labels = getLabel(storedLabels, question);

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
