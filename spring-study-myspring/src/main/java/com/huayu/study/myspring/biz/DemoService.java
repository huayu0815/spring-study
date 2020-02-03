package com.huayu.study.myspring.biz;

import lombok.Data;

/**
 * Created by zhaohuayu on 16/12/25.
 */
@Data
public class DemoService {

    private String name;

    private int age;

    public void sayUserInfo() {
        System.out.printf("hello,my name is %s, I'm %d years old!", name, age);
    }
}
