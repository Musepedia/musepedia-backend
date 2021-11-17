package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.Text;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TextService extends IService<Text> {

    List<String> selectByLabel(String label);

    String getText(String question);

    List<String> selectAllLabels();
}
