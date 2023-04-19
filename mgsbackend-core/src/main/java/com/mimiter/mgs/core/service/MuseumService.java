package com.mimiter.mgs.core.service;

import com.mimiter.mgs.model.entity.Museum;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 博物馆服务
 */
public interface MuseumService extends IService<Museum> {

    List<Museum> selectAllServicedMuseums();

    boolean hasPermission(Long museumId, int permission);
}
