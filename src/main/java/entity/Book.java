package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
