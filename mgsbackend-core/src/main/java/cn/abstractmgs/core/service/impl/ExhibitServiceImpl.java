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

    private List<Integer> exhibitIdToInteger(List<String> exhibitIds) {
        List<Integer> res = new ArrayList<>();
        for (String ids : exhibitIds) {
            for (String id : ids.split(",")) {
                res.add(Integer.parseInt(id));
            }
        }

        return res;
    }

    @Override
    public Exhibit getExhibitInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    public List<String> selectRandomExhibitId(int limitPerExhibitionHall) {
        return baseMapper.selectRandomExhibitId(limitPerExhibitionHall);
    }

    @Override
    public List<Exhibit> selectRandomExhibits(List<Integer> ids) {
        return baseMapper.selectRandomExhibits(ids);
    }

    @Override
    public List<Exhibit> getRandomExhibits(int limitPerExhibitionHall) {
        List<Integer> exhibitIds = exhibitIdToInteger(selectRandomExhibitId(limitPerExhibitionHall));
        return selectRandomExhibits(exhibitIds);
    }

    @Override
    public String selectExhibitFigureUrlByLabel(String label) {
        return baseMapper.selectExhibitFigureUrlByLabel(label);
    }

    @Override
    public List<Exhibit> getExhibitsInSameExhibitionHall(Long id) {
        return baseMapper.getExhibitsInSameExhibitionHall(id);
    }

    @Override
    public Long selectExhibitionHallIdByExhibitId(Long id) {
        return baseMapper.selectExhibitionHallIdByExhibitId(id);
    }

    @Override
    public Long selectExhibitIdByLabel(String label) {
        return baseMapper.selectExhibitIdByLabel(label);
    }

    @Override
    public boolean isSameExhibitionHall(int id1, int id2) {
        return baseMapper.isSameExhibitionHall(id1, id2);
    }


}
