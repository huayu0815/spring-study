package com.huayu.study.myspring;

import com.huayu.study.myspring.biz.DemoService;
import com.huayu.study.myspring.framework.ApplicationContext;

/**
 * Created by zhaohuayu on 16/12/25.
 */
public class Application {

    public static void main(String[] args) throws Exception {
        //加载配置文件，初始化容器
        ApplicationContext.init("ioc.yaml");
        //bean获取
        DemoService demoService = (DemoService) ApplicationContext.getBean("demoService") ;
        demoService.sayUserInfo();
    }
}
