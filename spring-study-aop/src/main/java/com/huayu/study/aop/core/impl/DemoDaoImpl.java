package com.huayu.study.aop.core.impl;

import com.huayu.study.aop.core.DemoDao;
import org.springframework.stereotype.Service;

import java.beans.Transient;

/**
 * Created by zhaohuayu on 16/12/27.
 */
@Service
public class DemoDaoImpl implements DemoDao {

    public void sayHelloWorld() {
        System.out.println("hello world from dao");
    }
}
