package p6;

import entity.Author;
import org.junit.Test;
import util.InitData;

import java.util.List;

public class Test0 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test3() {
        authors.stream()
                .map(Author::getAge)
                .map(String::valueOf)
                .forEach(System.out::println);
    }
    @Test
    public void test4() {
        StringBuilder sb = new StringBuilder();
        authors.stream()
                .map(Author::getName)
                .forEach(sb::append);
        System.out.println(sb.toString());
    }
    @Test
    public void test5() {
        authors.stream()
                .map(Author::getName)
                .map(StringBuilder::new)
                .map(sb -> sb.append("-sb").toString())
                .forEach(System.out::println);
    }
    @Test
    public void test6() {
        authors.stream()
                .mapToInt(Author::getAge)
                .map(age -> age+10)
                .filter(age -> age>18)
                .map(age -> age+2)
                .forEach(System.out::println);
    }
}
