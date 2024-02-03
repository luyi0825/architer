package io.github.architers.model.query;

import io.github.architers.common.json.ArchiterTypeReference;
import io.github.architers.common.json.JsonUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class ResourceDynamicColumnConfigLoad implements DynamicColumnConfigLoad {

    private final String resourceFile = "classpath:dynamicColumnConfig.json";

    @Override
    public void load() {
        InputStream inputStream;
        try {
            File file;
            file = ResourceUtils.getFile(resourceFile);
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            return;
        }
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        StringBuilder str = new StringBuilder();
        if (s.hasNext()) {
            str.append(s.next());
        }

        Map<String, List<DynamicColumnConfig>> dynamicColumnConfigMap = JsonUtils.parseObject(str.toString(),
                new ArchiterTypeReference<>() {});
        dynamicColumnConfigMap.forEach((code, configs) -> {
            if (!StringUtils.hasText(code)) {
                throw new IllegalArgumentException("动态列code不能为空");
            }
            try {
                DynamicConditionManager.registerColumnConditions(code, configs);
            } catch (Exception e) {
                throw new IllegalArgumentException("动态列code配置有误", e);
            }
        });
    }
}
