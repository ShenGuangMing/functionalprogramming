package p4;

import entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.InitData;

import java.util.Arrays;
import java.util.List;
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test0() {
        //打印姓名长度大于1的作家姓名
        authors.stream()
                .filter(author -> author.getName().length() > 1)//过滤筛选
                .forEach(author -> log.debug("Author : {}", author.getName()));
    }
    @Test
    public void test1() {
        //打印所有作者的姓名
        authors.stream()
                //将流中的元素由Author--->String
                .map(Author::getName)
                .forEach(name -> log.debug("Author : {}", name));
    }
    @Test
    public void test2() {
        //将所有作者的年龄加10打印
        authors.stream()
                .map(Author::getAge)
                .map(age -> age+10)
                .forEach(age -> log.debug("age = {}", age));
    }
    @Test
    public void test3() {
        //打印所有作家的姓名，姓名不能有重复的
        authors.stream()
                .map(Author::getName)
                .distinct()
                .forEach(name -> log.debug("name = {}", name));
        //打印作者信息，不能有重复
        authors.stream()
                .distinct()
                .forEach(System.out::println);
    }
    @Test
    public void test4() {
        //实现Comparable接口：对流中元素按照年龄进行降序排序，并且要求不能有重复的元素
        authors.stream()
                .distinct()
                .sorted()
                .forEach(System.out::println);
        //没有实现接口方法：
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge()-o2.getAge() )
                .forEach(System.out::println);
    }
    @Test
    public void test5() {
        //对流中元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两位作家
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .limit(2)
                .forEach(System.out::println);
    }
    @Test
    public void test6() {
        //对流中元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两位作家
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> -(o1.getAge()-o2.getAge()))
                .skip(1)
                .forEach(System.out::println);
    }
    @Test
    public void test7() {
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()//去重
                .forEach(book -> log.debug("{}", book));
    }
    @Test
    public void test8() {
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                //将字符数组转化为流
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))
                //去重
                .distinct()
                .forEach(category -> log.debug("{}",  category));
    }
}
