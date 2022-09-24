package p7;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

public class Test0 {
    @Test
    public void test0() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Optional<Integer> reduce = stream.parallel()//并行流
                .filter(num -> num > 5)
                .reduce(Integer::sum);
        reduce.ifPresent(System.out::println);
    }
}
