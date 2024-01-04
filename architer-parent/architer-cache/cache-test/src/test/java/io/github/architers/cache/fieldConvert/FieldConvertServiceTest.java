package io.github.architers.cache.fieldConvert;

import com.alibaba.fastjson.JSON;
import io.github.architers.cache.entity.FieldConvertNoCache;
import io.github.architers.cache.service.FieldConvertService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class FieldConvertServiceTest {

    @Resource
    private FieldConvertService fieldConvertService;

    @Test
    public void convertNoCache() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(i + "");
        }
        fieldConvertService.convertNoCacheList(codes);
        List<FieldConvertNoCache> list = fieldConvertService.convertNoCacheList(codes);
        System.out.println(JSON.toJSON(list));
    }

    @Test
    public void convertRemoteCache() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(i + "");
        }
        fieldConvertService.convertRemoteCache(codes);
        Object data = fieldConvertService.convertRemoteCache(codes);
        System.out.println(JSON.toJSON(data));
    }

    @Test
    public void convertRemoteCache_mush() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            codes.add(i + "");
            if (codes.size() == 100) {
                fieldConvertService.convertRemoteCache(codes);
                Object data = fieldConvertService.convertRemoteCache(codes);
                System.out.println(JSON.toJSON(data));
                codes.clear();
            }
        }
        fieldConvertService.convertRemoteCache(codes);
        Object data = fieldConvertService.convertRemoteCache(codes);
        System.out.println(JSON.toJSON(data));
    }

    @Test
    public void convertLocalCache() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(i + "");
        }
        fieldConvertService.convertLocalCache(codes);
        fieldConvertService.convertLocalCache(codes);
        Object data = fieldConvertService.convertLocalCache(codes);
        System.out.println(JSON.toJSON(data));
    }

    @Test
    public void convertLocalCache_mush() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            codes.add((int) (Math.random() * 1000000) + "");
            if (codes.size() == 100) {
                fieldConvertService.convertLocalCache(codes);
                Object data = fieldConvertService.convertLocalCache(codes);
                System.out.println(JSON.toJSON(data));
                codes.clear();
            }
        }
    }

    @Test
    public void convertBothCache() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(i + "");
        }
        fieldConvertService.convertBothCache(codes);
        fieldConvertService.convertBothCache(codes);
        Object data = fieldConvertService.convertBothCache(codes);
        System.out.println(JSON.toJSON(data));
    }


}
