package com.zp.zptest.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
public class BookService {

    @Cacheable("book")
    public Book getByIsbn(String isbn){
        try {
            Thread.sleep(1000);
            System.out.println("没有执行缓存取数据...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Book(isbn,"111");
    }

    public static void main(String[] args) {
        BookService bookService = new BookService();
        System.out.println(bookService.getByIsbn("123"));//,"颈椎病康复指南"
        System.out.println(bookService.getByIsbn("123"));
        System.out.println(bookService.getByIsbn("345")); //,"眼保健操步骤")
        System.out.println(bookService.getByIsbn("123"));
        System.out.println(bookService.getByIsbn("345"));
        System.out.println(bookService.getByIsbn("345"));
        System.out.println(bookService.getByIsbn("345"));
        System.out.println(bookService.getByIsbn("123"));
    }
}
