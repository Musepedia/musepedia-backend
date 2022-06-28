package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.Museum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MuseumService extends IService<Museum> {

    List<Museum> selectAllMuseums();

    List<Museum> selectAllServicedMuseums();
}
