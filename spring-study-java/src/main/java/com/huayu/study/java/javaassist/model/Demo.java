package com.huayu.study.java.javaassist.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class Demo {
    private String name;
    private int age;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
