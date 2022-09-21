package p4;

import entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import util.InitData;

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
        authors.stream()
                .map(Author::getName)
                .forEach(name -> log.debug("Author : {}", name));
    }
}
