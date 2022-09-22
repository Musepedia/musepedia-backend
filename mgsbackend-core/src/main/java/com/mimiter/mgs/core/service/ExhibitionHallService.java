package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.ExhibitionHall;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExhibitionHallService extends IService<ExhibitionHall> {

    List<Integer> selectExhibitionHallIds(@Param("id") Long id);
}
