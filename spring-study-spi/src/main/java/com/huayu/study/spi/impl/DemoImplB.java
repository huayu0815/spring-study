package com.huayu.study.spi.impl;

import com.huayu.study.spi.service.Demo;

public class DemoImplB implements Demo {
    @Override
    public void sayHello() {
        System.out.println("Hello,B");
    }
}
