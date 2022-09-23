package p4;

import entity.Author;
import entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.InitData;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test0() {
        //可以直接进行遍历
        authors
                .forEach(author -> log.debug("name ：{}", author.getName()));
        System.out.println("---------------------------");
        //可以转化成流再进行遍历
        authors.stream()
                .forEach(author -> log.debug("name ：{}", author.getName()));

    }
    @Test
    public void test1() {
        //打印作家的所有书籍，注意去重
        long count = authors.stream()
                .distinct()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        log.debug("count = {}", count);
    }
    @Test
    public void test2() {
        //分别获取这些作家的书籍中分数最高和最低并打印
        for (Author author : authors) {
            Optional<Integer> max = author.getBooks().stream()
                    //转化为分数
                    .map(Book::getScore)
                    .max(Integer::compare);

            Optional<Integer> min = author.getBooks().stream()
                    //转化为分数
                    .map(Book::getScore)
                    .max(Integer::compare);
            log.debug("{} max {}", author.getName(), max.get());
            log.debug("{} min {}", author.getName(), min.get());
        }
    }
    @Test
    public void test3() {
        //求书籍中分数最高的
        Optional<Integer> max = authors.stream()
                .distinct()//去重
                .flatMap(author -> author.getBooks().stream())
                .distinct()//去重
                //转元素类型
                .map(Book::getScore)
                .max(Integer::compare);
        log.debug("max {}", max.get());
        //求书籍中分数最低的
        Optional<Integer> min = authors.stream()
                .distinct()//去重
                .flatMap(author -> author.getBooks().stream())
                .distinct()//去重
                //转元素类型
                .map(Book::getScore)
                .min(Integer::compare);
        log.debug("min {}", min.get());
    }
    @Test
    public void test4() {
        //分别获取这些作家的书籍中分数最高和最低并打印，注意去重
        //1.获取作家流
        List<Author> list = authors.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list.size());
        for (Author author : list) {
            //max
            Optional<Integer> max = author.getBooks().stream()
                    .map(Book::getScore)
                    .max(Integer::compare);
            //min
            Optional<Integer> min = author.getBooks().stream()
                    .map(Book::getScore)
                    .min(Integer::compare);

            log.debug("{} max {}", author.getName(), max.get());
            log.debug("{} min {}", author.getName(), min.get());
        }
    }
    @Test
    public void test5() {
        //获取所有的书
        Set<Book> collect = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .collect(Collectors.toSet());
        for (Book book : collect) {
            System.out.println(book);
        }
    }
    @Test
    public void test6() {
        Map<String, List<Book>> map = authors.stream()
                .distinct()//去重
                .collect(Collectors.toMap(Author::getName, Author::getBooks));
        for (String name : map.keySet()) {
            log.debug("{} books {}", name, map.get(name));
        }
    }
}
