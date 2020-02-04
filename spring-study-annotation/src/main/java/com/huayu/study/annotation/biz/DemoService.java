package com.huayu.study.annotation.biz;

import com.huayu.study.annotation.core.DemoDao;
import com.huayu.study.annotation.document.MyAnnotation;
import org.springframework.beans.factory.annotation.Autowired;

@MyAnnotation(name = "demoService123", age=100)
public class DemoService {

    private int age;

    @Autowired
    DemoDao demoDao;

    public void sayHello() {
        System.out.println("hello world! Service! age=" + age);
        demoDao.sayHello();
    }
}
