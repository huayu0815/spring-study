package com.huayu.study.dubbo.provider;

import com.huayu.study.dubbo.client.DemoService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        String content = "hello!" + name;
        System.out.println(content);
        return content;
    }
}
