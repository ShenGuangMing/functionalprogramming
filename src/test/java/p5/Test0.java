package p5;

import entity.Author;
import org.junit.Test;
import util.InitData;

import java.util.List;
import java.util.function.Predicate;

public class Test0 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test0() {
        authors.stream()
                .filter(
                        ((Predicate<Author>) author -> author.getAge() < 17)
                            .and(author -> author.getName().length() > 1)
                )
                .forEach(System.out::println);
    }
    @Test
    public void test1() {
        authors.stream()
                .filter(
                        ((Predicate<Author>) author -> author.getAge() > 17)
                                .or(author -> author.getName().length() > 2)
                )
                .forEach(System.out::println);
    }
    @Test
    public void test2() {
        authors.stream()
                .filter(
                        ((Predicate<Author>) author -> author.getAge() > 17)
                                .negate()
                )
                .forEach(System.out::println);
    }

}
