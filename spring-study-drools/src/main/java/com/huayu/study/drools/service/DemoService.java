package com.huayu.study.drools.service;

import com.huayu.study.drools.model.Person;
import org.springframework.stereotype.Service;


@Service
public class DemoService {

    public void handle1(Person person) {
        System.out.println(person.getName() + ":住院");
    }

    public void handle2(Person person) {
        System.out.println(person.getName() + ":隔离");
    }}
