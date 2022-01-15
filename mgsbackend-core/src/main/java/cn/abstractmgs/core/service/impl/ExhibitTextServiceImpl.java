package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.repository.ExhibitTextRepository;
import cn.abstractmgs.core.service.ExhibitTextService;
import cn.abstractmgs.core.utils.NLPTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("exhibitTextService")
public class ExhibitTextServiceImpl extends ServiceImpl<ExhibitTextRepository, ExhibitText> implements ExhibitTextService {

    private List<String> getLabel(List<String> labels, String question) {
        NLPTool nlpTool = new NLPTool(question);
        nlpTool.updateCustomDictionary(labels);

        return nlpTool.getNoun();
    }

    @Override
    public List<String> selectByLabel(List<String> labels) {
        return baseMapper.selectByLabel(labels);
    }

    @Override
    public List<String> selectAllLabelsWithAliases() {
        return baseMapper.selectAllLabelsWithAliases();
    }

    @Override
    public List<String> getAllTexts(String question) {
        List<String> storedLabels = selectAllLabelsWithAliases();

        List<String> labels = getLabel(storedLabels, question);
        List<String> texts = selectByLabel(labels);

        return texts.size() >= MAX_TEXTS_COUNT
                ? null
                : texts;
    }
}
