package com.huayu.study.annotation.core;

import org.springframework.stereotype.Service;

@Service
public class DemoDao {
    public void sayHello() {
        System.out.println("hello world! Dao");
    }
}
