# 函数式编程-Stream流
# 1 概述

## 1.1 为什么要学？
- 能够看懂公司的代码
- 大数量下处理集合效率高
- 代码可读性高
- 消灭嵌套地狱

观察下面代码，一堆嵌套循环，看着是否很难受
```java
class Test {
    /*
    查询未成年作家评分在70分以上的数据，由于洋流影响所以
    作家的书籍可能重复，需要进行去重        
     */
    public void method() {
        List<Book> bookList = new ArrayList<Book>();
        //set具有去重的效果
        Set<Book> uniqueBookValues = new HashSet<Book>();
        Set<Author> uniqueAuthorValues = new HashSet<Author>();
        for (Author author : uniqueAuthorValues) {
            if (uniqueAuthorValues.add(author)) {
                if (author.getAge() < 18) {
                    List<Book> books = author.getBooks();
                    for (Book book : books) {
                        if (book.getSore() > 70) {
                            if (uniqueBookValues.add(book)) {
                                BookList.add(book);
                            }
                        }
                    }
                }
            }
        }
    }
}
```
那我们可以使用函数式编程进行修改
```java
class Test {
    /*
    查询未成年作家评分在70分以上的数据，由于洋流影响所以
    作家的书籍可能重复，需要进行去重        
     */
    public void method() {
        List<Book> collect = authors.stream()
                .distinct()
                .filter(author -> author.getAge() < 18)
                .map(author -> author.getBooks())
                .flatMap(Collection::stream)
                .filter(book -> book.getSore > 70)
                .distinct()
                .collect(Collection.toList());
    }
}
```

## 1.2 函数式编程思想
### 1.2.1 概念
面对对象思想需要关注用什么对象完成什么事情，而函数式编程思想
就类似于我们数学中的函数。它主要关注的是数据进行什么操作。
### 1.2.2 优点
- 代码简洁，开发快速
- 接近自然语言，易于理解
- 易于“并发编程”


# Lambda表达式
## 2.1 概述 
Lambad是JDK8的一个语法糖。它可以
对某些匿名内部类的写法进行进化。它是函数式编程的重要体现。
让我们不用关注是什么对象，而是更加关注对数据进行什么操作。

## 2.2 原则
> 可推导可省略
> 
## 2.3 基本格式
```text
(参数列表) -> {代码}
```
例一：

创建线程使用Runnable匿名类并启动
```java
class Test {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running");
            }
        }, "t1").start();
    }
}
```
> 我们使用Lambda来优化它
```java
class Test {
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("Running");
        }, "t1").start();
    }
}
```
例二：
```java
@Slf4j
public class Test0 {
    //使用Lambda传参calculateNun()方法
    @Test
    public void test1() {
        int result = calculateNun(10, 20, (x, y) ->{
            return  x * y;
        });
        System.out.println(result);
    }

    //将两个数进行运算
    public static int calculateNun(int a, int b, IntBinaryOperator operator) {
        return operator.applyAsInt(a, b);
    }
}
```
例三
```java
@Slf4j
public class Test0 {
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
}
```
例四

```java
@Slf4j
public class Test0 {
    //类型转化
    public static <R> R typeConvert(String num, Function<String, R> function) {
        return function.apply(num);
    }
    @Test
    public void test3() {
        Integer result = typeConvert("123", (x) -> {
            return Integer.parseInt(x);
        });
        log.debug("convertAfter: {}", result);
    }
}
```
## 2.4 省略规则
- 参数类型可以省略
- 方法体只有一句代码时，大括号return或唯一一句代码的分号
可以省略
- 方法只有一个参数时，小括号可以省略
- 以上的规则记不住也可以省略不记




