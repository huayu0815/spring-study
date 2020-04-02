package test;

import com.huayu.study.spi.service.Demo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DemoTest {

    @Test
    public void testSPI() {
        ServiceLoader<Demo> demos = ServiceLoader.load(Demo.class);
        for(Demo demo: demos) {
            demo.sayHello();
        }
        List a = new ArrayList<>();
    }
}
