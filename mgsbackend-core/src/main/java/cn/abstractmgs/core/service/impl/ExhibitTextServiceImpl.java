package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.ExhibitText;
import cn.abstractmgs.core.repository.ExhibitTextRepository;
import cn.abstractmgs.core.service.ExhibitTextService;
import cn.abstractmgs.core.utils.NLPTool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("exhibitTextService")
public class ExhibitTextServiceImpl extends ServiceImpl<ExhibitTextRepository, ExhibitText> implements ExhibitTextService {

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
    public Integer selectExhibitIdByLabel(String label) {
        return baseMapper.selectExhibitIdByLabel(label);
    }

    @Override
    public List<String> selectExhibitTextByExhibitId(int id) {
        return baseMapper.selectExhibitTextByExhibitId(id);
    }

    @Override
    public List<String> getAllTexts(String question) {
        List<String> storedLabels = selectAllLabels();

        List<String> labels = getLabel(storedLabels, question);
        List<Integer> exhibitIds = new ArrayList<>();
        for (String label : labels) {
            Integer exhibitId = selectExhibitIdByLabel(label);
            if (exhibitId != null)
                exhibitIds.add(exhibitId);
        }

        return exhibitIds.size() != 1
                ? null
                : selectExhibitTextByExhibitId(exhibitIds.get(0));
    }
}
