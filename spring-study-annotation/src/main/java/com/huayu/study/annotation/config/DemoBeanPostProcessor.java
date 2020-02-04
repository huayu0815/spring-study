package com.huayu.study.annotation.config;

import com.huayu.study.annotation.biz.DemoService;
import com.huayu.study.annotation.document.MyAnnotation;
import org.bouncycastle.asn1.cms.MetaData;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.StandardAnnotationMetadata;

import java.lang.reflect.Field;

@Configuration
public class DemoBeanPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DemoService) {
            System.out.println("开始实例化" + beanName);
            StandardAnnotationMetadata metadata = new StandardAnnotationMetadata(DemoService.class, true);
            if (metadata.hasAnnotation(MyAnnotation.class.getName())) {
                int age = (Integer)metadata.getAnnotationAttributes(MyAnnotation.class.getName()).get("age");
                try {
                    Field ageField = DemoService.class.getDeclaredField("age");
                    ageField.setAccessible(true);
                    ageField.setInt(bean, age);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DemoService) {
            System.out.println("实例化结束" + beanName);
        }
        return bean;
    }
}
