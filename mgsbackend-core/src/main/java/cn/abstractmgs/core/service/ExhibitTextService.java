package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.ExhibitText;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExhibitTextService extends IService<ExhibitText> {

    List<String> selectByLabel(String label);

    Integer selectExhibitIdByLabel(String label);

    List<String> selectExhibitTextByExhibitId(int id);

    List<String> getAllTexts(String question);

    List<String> selectAllLabels();
}
