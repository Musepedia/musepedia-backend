package com.mimiter.mgs.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mimiter.mgs.model.entity.GPTCompletion;

public interface GPTService extends IService<GPTCompletion> {

    GPTCompletion getGPTCompletion(String question, Long museumId);
}
