package com.zp.zptest.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author zhoupei
 * @create 2021/4/13
 **/
@Component
public class AppRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

    private final BookService bookService;

    public AppRunner(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(".... Fetching books");
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-234"));
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-1234"));
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-234"));
        logger.info("isbn-1234 -->" + bookService.getByIsbn("isbn-234"));
    }
}
