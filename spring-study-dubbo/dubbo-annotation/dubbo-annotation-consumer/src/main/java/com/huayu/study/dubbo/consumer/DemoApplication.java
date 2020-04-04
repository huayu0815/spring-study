package com.huayu.study.dubbo.consumer;

import com.huayu.study.dubbo.client.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class DemoApplication {

    @Reference
    private DemoService demoService;

    public void sayHello(String name) {
        System.out.println(demoService.sayHello(name));
    }
}
