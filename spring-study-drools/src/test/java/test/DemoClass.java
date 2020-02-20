package test;


import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.huayu.study.drools.Application;
import com.huayu.study.drools.model.Person;
import com.huayu.study.drools.service.DemoService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DemoClass {

    @Autowired
    private DemoService demoService;


    @Resource(name = "demoKieContainer")
    private KieContainer kieContainer;

    @Test
    public void demoTest() {

        Person person1 = new Person(1L, "小a", true, Lists.newArrayList());
        Person person2 = new Person(2L, "小b", true, Lists.newArrayList());
        Person person3 = new Person(3L, "小c", false, Lists.newArrayList(person1.getUid(),person2.getUid()));
        Person person4 = new Person(4L, "小d", false, Lists.newArrayList(person3.getUid()));

        Set<Person> set1 = Sets.newHashSet();
        Set<Person> set2 = Sets.newHashSet();


        KieSession kieSession = kieContainer.newKieSession();
        Lists.newArrayList(person1, person2, person3, person4).forEach(kieSession::insert);
        kieSession.setGlobal("set1", set1);
        kieSession.setGlobal("set2", set2);
        //kieSession.setGlobal("demoService", demoService);
        int count = kieSession.fireAllRules();
        kieSession.dispose();
        System.out.println(count);
        set1.forEach(demoService::handle1);
        set2.forEach(demoService::handle2);

    }


}
