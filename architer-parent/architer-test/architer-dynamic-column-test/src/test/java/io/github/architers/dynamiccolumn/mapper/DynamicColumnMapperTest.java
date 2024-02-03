package io.github.architers.dynamiccolumn.mapper;

import io.github.architers.common.json.JsonUtils;
import io.github.architers.model.query.ConditionUtils;
import io.github.architers.model.query.DynamicColumnConditions;
import io.github.architers.model.query.DynamicFieldConditions;
import io.github.architers.model.query.WhereOperator;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DynamicColumnMapperTest {

    @Resource
    private DynamicColumnMapper dynamicColumnMapper;

    @Test
    void getDynamicList() {
        DynamicFieldConditions dynamicFieldConditions = new DynamicFieldConditions();
        Set<String> columns = new HashSet<>();
        columns.add("id");
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * 6 + 1);
            columns.add("column" + index);
        }
        dynamicFieldConditions.setFieldNames(columns);

        dynamicFieldConditions.setWheres(getWheres());
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    private List<DynamicFieldConditions.Where> getWheres() {
        List<DynamicFieldConditions.Where> wheres = new ArrayList<>(2);
        // wheres.add(new DynamicFieldConditions.Where("id", WhereOperator.EQ, "1"));

        // wheres.add(new DynamicFieldConditions.Where("column1", WhereOperator.LIKE_LEFT, "row1"));
        // wheres.add(new DynamicFieldConditions.Where("id", WhereOperator.between, "1,2"));
        wheres.add(new DynamicFieldConditions.Where("id", WhereOperator.notBetween, "1,2"));
        return wheres;
    }

}