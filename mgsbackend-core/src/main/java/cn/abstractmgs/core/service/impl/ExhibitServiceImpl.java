package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.service.ExhibitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public Exhibit selectInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    public List<String> selectRandomExhibitId(int number) {
        return baseMapper.selectRandomExhibitId(number);
    }

    @Override
    public List<Exhibit> selectRandomExhibitPreference() {
        List<Integer> randomExhibitIds = exhibitIdToInteger(selectRandomExhibitId(4));
        return baseMapper.selectRandomExhibitPreference(randomExhibitIds);
    }

    @Override
    @Deprecated
    public Exhibit selectTest(Long id) {
        QueryWrapper<Exhibit> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("exhibit_label", "exhibit_description", "exhibit_url").eq("exhibit_id", id);
        return baseMapper.selectOne(queryWrapper);
    }
}
