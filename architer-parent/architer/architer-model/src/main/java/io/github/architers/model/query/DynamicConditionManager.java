package io.github.architers.model.query;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 动态列管理
 *
 * @author luyi
 * @since 1.0.3
 */
@Slf4j
public class DynamicConditionManager {


    static {

    }


    private static final Map<String, List<DynamicColumnConfig>> columnConditionsMap = new HashMap<>();

    static Map<String/*code*/, Map<String/*字段名*/, DynamicColumnConfig>> fieldColumnMap = new HashMap<>();


    public static void registerColumnConditions(String code, List<DynamicColumnConfig> columnConfigs) {
        synchronized (DynamicConditionManager.class) {
            List<DynamicColumnConfig> oldConfigs = columnConditionsMap.putIfAbsent(code, columnConfigs);
            fieldColumnMap.put(code, columnConfigs.stream().collect(Collectors.toMap(DynamicColumnConfig::getFieldName, e -> e)));
            if (oldConfigs != null) {
                log.warn("动态列配置已经存在:{}", code);
            }
        }
        log.info("registerColumnConditions:{}", code);
    }

    public static List<DynamicColumnConfig> getByCode(String code) {
        return columnConditionsMap.get(code);
    }

    public static Map<String/*字段名*/, DynamicColumnConfig> getMapByCode(String code) {
        return fieldColumnMap.get(code);
    }


}
