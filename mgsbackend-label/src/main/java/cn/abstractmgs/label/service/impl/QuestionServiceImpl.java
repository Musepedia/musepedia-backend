package cn.abstractmgs.label.service.impl;

import cn.abstractmgs.label.model.entity.Question;
import cn.abstractmgs.label.repository.QuestionRepository;
import cn.abstractmgs.label.service.QuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionRepository, Question> implements QuestionService {

    @Override
    public List<Question> selectByParagraphId(Long id) {
        return baseMapper.selectByParagraphId(id);
    }
}
