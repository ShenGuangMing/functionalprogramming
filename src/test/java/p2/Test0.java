package p2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

@Slf4j
public class Test0 {

    //创建线程使用Runnable匿名类并启动
    @Test
    public void test0() {

        //方式1
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        }).start();
        //方式2
        new Thread(() -> {
            log.debug("running");
        }).start();
    }

    //将两个数进行运算
    public static int calculateNun(int a, int b, IntBinaryOperator operator) {
        return operator.applyAsInt(a, b);
    }

    //使用Lambda传参calculateNun()方法
    @Test
    public void test1() {
        int result = calculateNun(10, 20, (x, y) -> {
            return x * y;
        });
        System.out.println(result);
    }

    public static void printNum(int[] arr, IntPredicate predicate) {
        for (int num : arr) {
            if (predicate.test(num)) {
                log.debug("even: {}", num);
            }
        }
    }

    @Test
    public void test2() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        printNum(arr, x -> {  //是否是偶数
            return x % 2 == 0;
        });
    }

    public static <R> R typeConvert(String num, Function<String, R> function) {
        return function.apply(num);
    }

    @Test
    public void test3() {
        Integer result = typeConvert("123", (x) -> {//字符数字转int
            return Integer.valueOf(x);
        });
        log.debug("convertAfter: {}", result);

        String s = typeConvert("name: ", (x) -> {
            return x + "SGMing";
        });
        log.debug("convertAfter: {}", s);
    }

    public static void foreachArr(List<Object> list, Consumer consumer) {
        for (Object object : list) {
            consumer.accept(object);
        }
    }

    @Test
    public void test4() {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(9);
        foreachArr(list, (x) -> {
            log.debug("{}", x);
        });
        foreachArr(list, x -> log.debug("{}", x) );
    }


}
