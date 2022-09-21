package p3;

import entity.Author;
import org.junit.Test;
import util.InitData;

import java.util.List;

public class Test0 {
    @Test
    public void test0() {
        List<Author> authors = InitData.initAuthor1();
        authors.stream()//把集合转化成流
                .distinct()//去重
                .filter(author -> author.getAge() < 18)
                .forEach(author -> System.out.println(author.getName()));

    }

}
