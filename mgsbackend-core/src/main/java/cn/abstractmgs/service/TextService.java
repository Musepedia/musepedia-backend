package cn.abstractmgs.service;

import cn.abstractmgs.model.entity.Text;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TextService extends IService<Text> {
    List<Text> selectByLabel(@Param("label") String label);
    String getText(String question);
}
