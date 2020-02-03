package com.huayu.study.aop;

import com.huayu.study.aop.biz.DemoService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configurable
@ComponentScan("com.huayu.study.aop")
@EnableAspectJAutoProxy
public class Application {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Application.class) ;
        DemoService demoService = (DemoService) context.getBean("demoService") ;
        demoService.sayHelloWorld();
    }
}
