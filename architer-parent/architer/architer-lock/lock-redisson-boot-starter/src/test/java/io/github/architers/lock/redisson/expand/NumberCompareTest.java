package io.github.architers.lock.redisson.expand;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NumberCompareTest {

    @Resource
    private NumberCompare numberCompare;

    @Test
    void geAndSet() {
        String key = "numberCompare";
        System.out.println(numberCompare.geAndSet(key,2L));
        System.out.println(numberCompare.geAndSet(key,2L));
        System.out.println(numberCompare.geAndSet(key,1L));
        System.out.println(numberCompare.geAndSet(key,3L));
    }

    @Test
    void geAndSetExpire() {
        String key = "numberCompare_ge_expire";
        System.out.println(numberCompare.geAndSet(key,2L,30));
        System.out.println(numberCompare.geAndSet(key,2L,60));
        System.out.println(numberCompare.geAndSet(key,1L,60));
        System.out.println(numberCompare.geAndSet(key,3L,60));
    }

    @Test
    void gtAndSetExpire() {
        String key = "numberCompare_gt_expire";
        System.out.println(numberCompare.gtAndSet(key,2L,30));
        System.out.println(numberCompare.gtAndSet(key,2L,60));
        System.out.println(numberCompare.gtAndSet(key,1L,60));
        System.out.println(numberCompare.gtAndSet(key,3L,60));
        System.out.println(numberCompare.gtAndSet(key,4L,-1));
    }
}