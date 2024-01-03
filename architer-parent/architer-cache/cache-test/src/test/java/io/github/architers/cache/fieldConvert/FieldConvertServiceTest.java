package io.github.architers.cache.fieldConvert;

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
    public void getByCodes() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            codes.add(i + "");
        }
        fieldConvertService.getByCodes(codes);
        fieldConvertService.getByCodes(codes);
    }
}
