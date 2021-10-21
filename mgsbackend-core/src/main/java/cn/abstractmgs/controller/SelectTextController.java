package cn.abstractmgs.controller;

import cn.abstractmgs.model.entity.Text;
import cn.abstractmgs.repository.TextRepository;
import cn.abstractmgs.service.TextService;
import cn.abstractmgs.service.impl.TextServiceImpl;
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
    /*
     * 仅测试用
     */

    @Autowired
    private TextService service;

    @GetMapping("api/select/text")
    public String selectText(@RequestParam(value = "question") String question) {
        return service.getText(question);
    }
}
