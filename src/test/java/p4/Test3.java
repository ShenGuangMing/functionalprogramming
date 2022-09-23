package p4;

import entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.InitData;

import java.util.List;

@Slf4j
public class Test3 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test0() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::sum);
        log.debug("年龄和：{}", reduce);
    }

    @Test
    public void test1() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::max);
        log.debug("年龄最大：{}", reduce);
    }

    @Test
    public void test2() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::min);
        log.debug("年龄最大：{}", reduce);
    }
}
