package p3;

import org.junit.Test;
import util.InitData;

public class Test0 {
    @Test
    public void test0() {
        InitData.initAuthor1().forEach(System.out::println);
    }
}
