package com.huayu.study.aop.biz;

import com.huayu.study.aop.document.Action;

/**
 * Created by zhaohuayu on 16/12/27.
 */
public interface DemoService {

    @Action(name="注解式拦截的sayHelloWorld操作")
    void sayHelloWorld();
}
