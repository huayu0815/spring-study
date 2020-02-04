package test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

public class DemoTest {

    @Test
    public void test() {
        List<String> a = Lists.newArrayList("a","b","c");
        a.stream().map(e -> e.toString()).collect(Collectors.toList());
    }
}
