package com.huayu.study.spi.impl;

import com.huayu.study.spi.service.Demo;

public class DemoImplA  implements Demo {
    @Override
    public void sayHello() {
        System.out.println("Hello,A");
    }
}
