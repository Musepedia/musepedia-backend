package cn.abstractmgs.controller;

import cn.abstractmgs.model.entity.Text;
import cn.abstractmgs.repository.TextRepository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SelectTextController {
    @Autowired
    private TextRepository textRepository;

    @GetMapping("api/select/text")
    public String selectText(@RequestParam(value = "label") String label) {
        List<Text> textList = textRepository.selectByLabel(label);

        if (textList.size() == 0)
            return "暂时无法回答这个问题";
        return textList.get(0).getText();
    }
}
