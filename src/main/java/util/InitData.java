package util;

import entity.Author;
import entity.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        b1.add(new Book(1L, "刀的两侧是光与暗",  "爱情,哲学", 88, "用一把刀分割爱恨"));
        b1.add(new Book(2L, "一个人不能死在同一把刀下",
                "成长,哲学", 99, "从失败中明悟哲学"));

        b2.add(new Book(3L, "那风吹不到的地方", "哲学,理想", 85, "用思维看世界尽头"));
        b2.add(new Book(3L, "那风吹不到的地方", "哲学,理想", 85, "用思维看世界尽头"));
        b2.add(new Book(4L, "那些白线，接与不接", "哲学", 56, "为自己的错误买单"));

        b3.add(new Book(5L, "你的剑就是我的剑", "爱情", 68, "一个武者的伴侣的宽容"));
        b3.add(new Book(6L, "风遇见", "传记,爱情", 80, "错过即是最好的相遇"));
        b3.add(new Book(6L, "风遇见", "传记,爱情", 80, "错过即是最好的相遇"));
        a1.setBooks(b1);
        a2.setBooks(b2);
        a3.setBooks(b3);
        a4.setBooks(b3);
        return new ArrayList<>(Arrays.asList(a1, a2, a3, a4));
    }
}
