package com.huayu.study.aop.document;

import java.lang.annotation.*;

/**
 * Created by zhaohuayu on 16/12/27.
 */
@Target(ElementType.METHOD) //该注解用于方法上
@Retention(RetentionPolicy.RUNTIME) //运行时使用
@Documented
public @interface Action {
    String name() ;
}
