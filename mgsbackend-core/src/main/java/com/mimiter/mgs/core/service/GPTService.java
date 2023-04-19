package com.mimiter.mgs.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mimiter.mgs.model.entity.ExhibitText;
import com.mimiter.mgs.model.entity.GPTCompletion;

import java.util.List;

public interface GPTService extends IService<GPTCompletion> {

    GPTCompletion getGPTCompletion(String question, Long museumId, List<ExhibitText> exhibitId);
}
