package com.zp.zptest.cache;

import lombok.Data;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
@Data
public class Book {
    private String isbn;
    private String name;

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
