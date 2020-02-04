package com.huayu.study.annotation;

import com.huayu.study.annotation.biz.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        DemoService demoService = applicationContext.getBean("demoService123", DemoService.class);
        demoService.sayHello();
    }
}
