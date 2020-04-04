package com.huayu.study.dubbo.consumer;

import com.huayu.study.dubbo.client.DemoService;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@ComponentScan
@EnableDubbo
public class Application {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class);
        context.start();
        DemoApplication demoApplication = (DemoApplication)context.getBean("demoApplication"); // 获取远程服务代理
        demoApplication.sayHello("annotation"); // 执行远程方法
    }
}
