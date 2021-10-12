package cn.abstractmgs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/another")
public class AnotherHello {

    @RequestMapping
    public String test(){
        return "another";
    }
}
