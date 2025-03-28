package com.mimiter.mgs.core.service.impl;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.mimiter.mgs.core.repository.ExhibitionHallRepository;
import com.mimiter.mgs.core.service.ExhibitionHallService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * {@inheritDoc}
 */
@Service("exhibitionHallService")
public class ExhibitionHallServiceImpl
        extends ServiceImpl<ExhibitionHallRepository, ExhibitionHall> implements ExhibitionHallService {

}
