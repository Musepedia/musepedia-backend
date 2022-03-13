package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExhibitTextService extends IService<ExhibitText> {

    int MAX_TEXTS_COUNT = 15;  // 允许至多抽取的text数量

    List<ExhibitText> selectByLabel(List<String> labels);

    List<ExhibitText> getAllTexts(String question);

    List<String> selectAllLabelsWithAliases();
}
