package io.github.architers.model.query;

import org.springframework.util.CollectionUtils;

import java.util.*;

public class DynamicColumnManager {


    static Map<String, List<ColumnConditions.Column>> columnMap = new HashMap<>();

    static Map<String, Map<String/*字段名*/, String/*列名*/>> fieldColumnMap = new HashMap<>();

    public static List<ColumnConditions.Column> getByCodeAndFieldNames(String code, Set<String> fieldNames) {
        List<ColumnConditions.Column> columns = columnMap.get(code);
        if (CollectionUtils.isEmpty(columns)) {
            return null;
        }
        List<ColumnConditions.Column> returnColumns = new ArrayList<>();
        for (ColumnConditions.Column column : columns) {
            if (fieldNames.contains(column.getFieldName())) {
                returnColumns.add(column);
            }
        }
        return returnColumns;
    }

    public static Map<String, String> getFieldColumns(String code) {
        return fieldColumnMap.get(code);
    }

    public static List<ColumnConditions.Column> getByCode(String code) {
        return columnMap.get(code);
    }


}
