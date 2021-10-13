package cn.abstractmgs.label.service.impl;

import cn.abstractmgs.label.model.entity.Paragraph;
import cn.abstractmgs.label.repository.ParagraphRepository;
import cn.abstractmgs.label.service.ParagraphService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service("paragraphService")
public class ParagraphServiceImpl extends ServiceImpl<ParagraphRepository, Paragraph> implements ParagraphService {
}
