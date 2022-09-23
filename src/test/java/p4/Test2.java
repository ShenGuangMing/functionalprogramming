package p4;

import entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.InitData;

import java.util.List;
import java.util.Optional;

@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();

    @Test
    public void test1() {
        //判断是否有年龄在29以上的作家
        boolean b = authors.stream()
                //一个为真，返回真， 全为假返回假
                .anyMatch(author -> author.getAge() > 29);
        log.debug("是否存在29岁以上的作者：{}", b);
    }
    @Test
    public void test2() {
        //判断所有作家是否都成年
        boolean b = authors.stream()
                //见假为假，全真为真
                .allMatch(author -> author.getAge() > 18);
        log.debug("是否所有的作家都成年了：{}", b);
    }
    @Test
    public void test3() {
        //判断所有作者否没有超过50岁
        boolean b = authors.stream()
                //如果都不符合就返回true，否则返回false
                .noneMatch(author -> author.getAge() > 50);
        log.debug("所有作家是否都没有超过50岁：{}", b);
    }
    @Test
    public void test4() {
        Optional<Author> one = authors.stream()
                .filter(author -> author.getAge() >= 18)
                .findAny();
        one.ifPresent(author -> log.debug("成年的作家：{}", author.getName()));
    }
    @Test
    public void test5() {
        Optional<Author> first = authors.stream()
                .distinct()
                .sorted((o1, o2) -> {
                    return o1.getAge() - o2.getAge();
                })
                .findFirst();
        first.ifPresent(author -> log.debug("第一个最小的作家：{}", author.getName()));
    }
}
