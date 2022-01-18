package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.service.ExhibitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("exhibitService")
public class ExhibitServiceImpl extends ServiceImpl<ExhibitRepository, Exhibit> implements ExhibitService {

    @Override
    public Exhibit getExhibitInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    public List<Exhibit> getRandomExhibits(int limit) {
        return baseMapper.getRandomExhibits(limit);
    }

}
