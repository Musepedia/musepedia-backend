package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.model.entity.ExhibitionHall;
import cn.abstractmgs.core.repository.ExhibitionHallRepository;
import cn.abstractmgs.core.service.ExhibitionHallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("exhibitionHallService")
public class ExhibitionHallServiceImpl extends ServiceImpl<ExhibitionHallRepository, ExhibitionHall> implements ExhibitionHallService {

    @Override
    public List<Integer> selectExhibitionHallIds(Long id) {
        return baseMapper.selectExhibitionHallIds(id);
    }
}
