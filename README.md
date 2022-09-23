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
## 3.3 快速入门
### 3.3.1 需求
打印所有年龄小于18岁的作者的名字，注意去重
```java

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
```
> 可以在使用流的位置打断点，调试中找到 Throw Current Stream Chain

## 3.4 常用操作
### 3.4.1 创建流
单例集合：`集合对象.stream()`
```text
    List<Author> authors = new ArrayList<Author>();
    Stream<Author>stream = authors.stream();
```
数组：Arrays.stream(数组)或者使用Stream.of(数组)创建
```text
    Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
    Stream<Integer> stream0 = Arrays.stream(arr);
    Stream<Integer> stream0 = Stream.of(arr);
```
双列集合：转化成单例集合后再创建
```text
    Map<String, Integer> map = new HashMap<String, Integer>();
    map.put("那笔小新", 19);
    map.put("黑子", 17);
    map.put("湘湘", 18);
    Stream<Map.Entry<String, Integer>> stream = map.entrySet().stream();
```
### 3.4.2 中间操作
     
**filter**
可以对流中的元素进行条件过滤，符合条件的会留在流中

例如：打印姓名长度大于1的作家姓名
```java
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

}
```
**map**
可以把流中元素或转化

例如：打印所有作家
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();

    @Test
    public void test1() {
        authors.stream()
                .map(Author::getName)//将流中的元素由Author--->String
                .forEach(name -> log.debug("Author : {}", name));
    }
}
```
**distinct**
可以去除流中重复的元素

例如：打印所有作家的姓名，不能有重复的
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
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
}
```
> **注意**
> 
> distinct方法以来Object的equals方法来判断是否是相同的对象。所以需要注意重写equals方法

**sorted**
可以对流中的元素进行排序

例如：对流中元素按照年龄进行降序排序，并且要求不能有重复的元素
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
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
}
```
> **注意**
> 
> 空参的sorted方法需要流中元素实现了Comparable接口

**limit**
可以设置流的最大长度，超出部分会被抛弃

例如：对流中元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两位作家的姓名
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
    public void test5() {
        //对流中元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两位作家的姓名
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .limit(2)
                .forEach(System.out::println);
    }
}
```

**skip**
跳过流中前n个元素，返回剩下的

例如：
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
   
    @Test
    public void test6() {
        //对流中元素按照年龄进行降序排序，并且要求不能有重复的元素，然后打印其中年龄最大的两位作家
        authors.stream()
                .distinct()
                .sorted((o1, o2) -> -(o1.getAge()-o2.getAge()))
                .skip(1)
                .forEach(System.out::println);
    }
}
```

**flatMap**
map只能把一个对象转化为另一个对象的元素，二flatMap可以把一个对象转化为多个对象流中的元素

例一：打印所有书籍的名字，要求去重
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test7() {
        authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()//去重
                .forEach(book -> log.debug("{}", book));
    }
}
```

例二：打印书籍的所有分类。要求分类进行去重。不能出现这种形式：哲学、爱情
```java
@Slf4j
public class Test0 {
    List<Author> authors = InitData.initAuthor1();
   
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
```

### 3.4.3 终结操作
**forEach**
对流中的元素进行遍历，我们通过传入的参数指定对遍历元素进行什么具体操作

例如：输出所有作家姓名
```java
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
}
```

**count**
可以获取当前流中元素个数

例子：打印作家的所有书籍，注意去重
```java
@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
 
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
}
```

**max&min**
获取流中的最值

例子：分别获取这些作家的书籍中分数最高和最低并打印
```java
@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
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
}
```

**collect**
把流转化成集合

例子：（list）分别获取这些作家的书籍中分数最高和最低并打印，注意去重
```java
@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
  
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
}
```

例子：（set）获取所有的书籍，注意去重
```java
@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
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
}
```

例子：（map）获取一个作者的所有书籍
```java
@Slf4j
public class Test1 {
    List<Author> authors = InitData.initAuthor1();
    
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
```

#### 查找和匹配

**anyMatch**

可以用来判断是否任意符合匹配条件元素，结果为boolean类型。

例子：判断是否有年龄在29以上的作家
```java
@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();

    @Test
    public void test1() {
        //判断是否有年龄在29以上的作家
        boolean b = authors.stream()
                //一个为真，返回真， 全为假返回假
                .anyMatch(author -> author.getAge() > 29);
        log.debug("是否存在29岁以上的作者：{}", b);
    }
}
```

**allMatch**

可以判断流中的元素是否都匹配条件，结果为boolean类型，见假为假，全真为真

例子：判断所有作家是否都成年
```java
@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test2() {
        //判断所有作家是否都成年
        boolean b = authors.stream()
                //见假为假，全真为真
                .allMatch(author -> author.getAge() > 18);
        log.debug("是否所有的作家都成年了：{}", b);
    }
}
```

**noneMatch**

可以判断流中元素是否都不满足条件，如果都不符合就返回true，否则返回false

例子：判断所有作者否没有超过50岁
```java
@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();

    @Test
    public void test3() {
        //判断所有作者否没有超过50岁
        boolean b = authors.stream()
                //如果都不符合就返回true，否则返回false
                .noneMatch(author -> author.getAge() > 50);
        log.debug("所有作家是否都没有超过50岁：{}", b);
    }
}
```

**findAny**

获取流中的任意元素。该方法没有办法保证获取的一定是流中第一个元素。

例子：获取任意一个大于18岁的作家，如果存在就输入他的姓名
```java
@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test4() {
        Optional<Author> one = authors.stream()
                .filter(author -> author.getAge() >= 18)
                .findAny();
        log.debug("成年的作者：{}", one.get().getName());
    }
}
```

**findFirst**

获取流中第一个元素

例子：返回第一个年龄最小的作家
```java
@Slf4j
public class Test2 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test5() {
        Optional<Author> first = authors.stream()
                .distinct()
                .sorted((o1, o2) -> {
                    return o1.getAge() - o2.getAge();
                })
                .findFirst();
        first.ifPresent(author -> log.debug("第一个最小的作家：{}", author.getName()));
    }
}
```

**reduce归并**

对流中的数据按照你指定的计算方式计算出一个结果（缩减操作）

reduce的作用是吧stream中的元素组合起来，我们可以传入一个初始值，它会按照我们的计算方式依次从流中的元素和在初始值的基础上进行计算，计算的结果再和后面的元素计算

他的内部计算方式：
```text
T result = identity;
for(T element : this stream) {
    result = accumulator.apply(result, element);
}
return result;
```
> 其中的identity就是我们通过方法传入的初始值，accumulator的apply具体进行什么计算也是我们通过方法参数来确定的
> 

例如：使用reduce求所有作者的年龄和
```java
@Slf4j
public class Test3 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test0() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::sum);
        log.debug("年龄和：{}", reduce);
    }
}
```

例如：使用reduce求所有作者中年龄最大值
```java
@Slf4j
public class Test3 {
    List<Author> authors = InitData.initAuthor1();
    @Test
    public void test1() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::max);
        log.debug("年龄最大：{}", reduce);
    }

}
```
例如：使用reduce求所有作者中年龄最小值
```java
@Slf4j
public class Test3 {
    List<Author> authors = InitData.initAuthor1();

    @Test
    public void test2() {
        //使用reduce求所有作者的年龄和
        Integer reduce = authors.stream()
                //去重
                .distinct()
                .map(Author::getAge)
                .reduce(0, Integer::min);
        log.debug("年龄最大：{}", reduce);
    }
}
```


