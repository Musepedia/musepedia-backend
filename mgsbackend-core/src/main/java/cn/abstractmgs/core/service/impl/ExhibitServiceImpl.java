package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.service.ExhibitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("exhibitService")
public class ExhibitServiceImpl extends ServiceImpl<ExhibitRepository, Exhibit> implements ExhibitService {

    @Override
    public Exhibit selectInfoById(Long id) {
        return baseMapper.selectInfoById(id);
    }

    @Override
    @Deprecated
    public Exhibit selectTest(Long id) {
        QueryWrapper<Exhibit> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("exhibit_label", "exhibit_description", "exhibit_url").eq("exhibit_id", id);
        return baseMapper.selectOne(queryWrapper);
    }
}
