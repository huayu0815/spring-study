package com.huayu.study.drools.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Long uid;
    private String name;
    private Boolean isDiagnosed;
    private List<Long> contact;

}
