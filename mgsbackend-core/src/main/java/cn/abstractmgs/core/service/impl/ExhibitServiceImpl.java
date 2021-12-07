package cn.abstractmgs.core.service.impl;

import cn.abstractmgs.core.model.entity.Exhibit;
import cn.abstractmgs.core.repository.ExhibitAliasRepository;
import cn.abstractmgs.core.repository.ExhibitRepository;
import cn.abstractmgs.core.service.ExhibitService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("exhibitService")
public class ExhibitServiceImpl extends ServiceImpl<ExhibitRepository, Exhibit> implements ExhibitService {

}
