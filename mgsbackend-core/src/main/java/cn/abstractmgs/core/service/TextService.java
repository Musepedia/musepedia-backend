package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TextService extends IService<ExhibitText> {

    List<String> selectByLabel(String label);

    String getText(String question);

    List<String> selectAllLabels();
}
