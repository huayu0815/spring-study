package com.huayu.study.annotation.document;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface MyAnnotation {
    String name();
    int age() default 1;
}
