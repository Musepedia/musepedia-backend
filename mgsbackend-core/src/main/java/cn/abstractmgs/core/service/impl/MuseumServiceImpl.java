package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Museum;
import cn.abstractmgs.core.repository.MuseumRepository;
import cn.abstractmgs.core.service.MuseumService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("museumService")
public class MuseumServiceImpl extends ServiceImpl<MuseumRepository, Museum> implements MuseumService {

    @Override
    public List<Museum> selectAllMuseums() {
        return baseMapper.selectAllMuseums();
    }

    @Override
    public List<Museum> selectAllServicedMuseums() {
        return baseMapper.selectAllServicedMuseums();
    }
}
