package com.huayu.study.aop.biz.impl;

import com.huayu.study.aop.biz.DemoService;
import com.huayu.study.aop.core.DemoDao;
import com.huayu.study.aop.document.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhaohuayu on 16/12/27.
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoDao demoDao;

    @Action(name="注解式拦截的sayHelloWorld操作")
    public void sayHelloWorld() {
        demoDao.sayHelloWorld();
        System.out.println("hello World from service");
    }
}
