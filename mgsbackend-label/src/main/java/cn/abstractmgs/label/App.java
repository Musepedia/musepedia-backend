package cn.abstractmgs.label;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan({"cn.abstractmgs.common","cn.abstractmgs.label"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
