package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.Exhibit;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface ExhibitService extends IService<Exhibit> {

    Exhibit getExhibitInfoById(Long id);

    List<Exhibit> getRandomExhibits(int limit);

}
