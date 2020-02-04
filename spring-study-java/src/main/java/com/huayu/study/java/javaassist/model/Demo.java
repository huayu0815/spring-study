package com.huayu.study.java.javaassist.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Demo {
    private String name = "java";
    private int age = 1;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
