package com.huayu.study.annotation.config;

import com.huayu.study.annotation.document.MyAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Configuration
public class DefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        // bean 的名字生成规则在AnnotationBeanNameGenerator
        //scanner.setBeanNameGenerator(new AnnotationBeanNameGenerator());
        scanner.setBeanNameGenerator(new AnnotationBeanNameGenerator() {

            @Override
            public String generateBeanName(BeanDefinition beanDefinition, BeanDefinitionRegistry beanDefinitionRegistry) {

                AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
                AnnotationMetadata amd = annotatedBeanDefinition.getMetadata();
                if (amd.hasAnnotation(MyAnnotation.class.getName())) {
                    return (String)amd.getAnnotationAttributes(MyAnnotation.class.getName()).get("name");
                } else {
                    return super.generateBeanName(beanDefinition, beanDefinitionRegistry);
                }
            }
        });
        // 设置哪些注解的扫描
        scanner.addIncludeFilter(new AnnotationTypeFilter(MyAnnotation.class));
        scanner.scan("com.huayu.study.annotation.biz");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
