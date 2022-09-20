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

```java
@Slf4j
public class Test0 {
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
        //方式1
        foreachArr(list, (x) -> {
            log.debug("{}", x);
        });
        //方式2，使用省略规则
        foreachArr(list, x -> log.debug("{}", x) );
    }
}
```

# 3. Stream流
## 3.1 概述
java8的Strean使用的是函数式编程，如同它的名字一样，它可以
用来对集合或数组进行链状流式的操作。可以方便让我们对集合
或数组操作。

## 3.2 案例数据准备
```java
@Data
@NoArgsConstructor//没有无参构造
@AllArgsConstructor
@EqualsAndHashCode//重写了equal和hashcode方法
public class Author {
    private Long id;
    private String name;
    private Integer age;
    private String intro;
    private List<Book> books;
}
```
```java
@Data
@NoArgsConstructor//没有无参构造
@AllArgsConstructor
@EqualsAndHashCode//重写了equal和hashcode方法
public class Book {
    private Long id;
    private String bookName;
    private String category;
    private Integer score;
    private String intro;
}
```
```java
public class InitData {
    public static List<Author> initAuthor1() {
        //创建一些作者对象
        Author a1 = new Author(1L, "梦多", 33, "一个从菜刀中明悟哲理的人", null);
        Author a2 = new Author(2L, "亚拉索", 15, "风不及我的思考速度", null);
        Author a3 = new Author(3L, "易", 14, "是世界限制了我的思考", null);
        Author a4 = new Author(3L, "易", 14, "是世界限制了我的思考", null);
        //书记列表
        List<Book> b1 = new ArrayList<Book>();
        List<Book> b2 = new ArrayList<Book>();
        List<Book> b3 = new ArrayList<Book>();
        b1.add(new Book(1L, "刀的两侧是光与暗",  "爱情，哲学", 88, "用一把刀分割爱恨"));
        b1.add(new Book(2L, "一个人不能死在同一把刀下",
                "成长，哲学", 99, "从失败中明悟哲学"));

        b2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "用思维看世界尽头"));
        b2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "用思维看世界尽头"));
        b2.add(new Book(4L, "那些白线，接与不接", "哲学", 56, "为自己的错误买单"));

        b3.add(new Book(5L, "你的剑就是我的剑", "爱情", 68, "一个武者的伴侣的宽容"));
        b3.add(new Book(6L, "风遇见", "传记", 80, "错过即是最好的相遇"));
        b3.add(new Book(6L, "风遇见", "传记", 80, "错过即是最好的相遇"));
        a1.setBooks(b1);
        a2.setBooks(b2);
        a3.setBooks(b3);
        return new ArrayList<>(Arrays.asList(a1, a2, a3));
    }
}
```


