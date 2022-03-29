package cn.abstractmgs.core.service;

import cn.abstractmgs.core.model.entity.ExhibitText;

import java.util.List;

public interface QAService {

    int getStatus(String answer);

    String getAnswer(String question);
}
