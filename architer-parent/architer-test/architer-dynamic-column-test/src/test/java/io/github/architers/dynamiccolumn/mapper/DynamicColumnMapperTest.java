package io.github.architers.dynamiccolumn.mapper;

import io.github.architers.common.json.JsonUtils;
import io.github.architers.query.dynimic.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.*;

@SpringBootTest
class DynamicColumnMapperTest {

    @Resource
    private DynamicColumnMapper dynamicColumnMapper;

    private DynamicFieldConditions dynamicFieldConditions() {
        DynamicFieldConditions dynamicFieldConditions = new DynamicFieldConditions();
        Set<String> columns = new HashSet<>();
        columns.add("id");
        for (int i = 0; i < 5; i++) {
            int index = (int) (Math.random() * 6 + 1);
            columns.add("column" + index);
        }
        dynamicFieldConditions.setFieldNames(columns);
        return dynamicFieldConditions;
    }


    @Test
    public void in() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.in, "1,2")));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void notIn() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.notIn, "1,2")));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void like() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("column1", WhereOperator.like, "row")));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void likeRight() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("column1", WhereOperator.likeRight, "row1")));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);
        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void equals() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.equal, 1)));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void notEquals() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.notEqual, 1)));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    /**
     * 大于等于
     */
    @Test
    public void ge() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.ge, 1)));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    /**
     * 大于等于
     */
    @Test
    public void gt() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.gt, 1)));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void between() {
        DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();

        List<Where> wheres = new ArrayList<>(2);
        wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.between, "1,3")));
        dynamicFieldConditions.setWheres(wheres);
        DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

        List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
        System.out.println(JsonUtils.toJsonString(list));
    }

    @Test
    public void notBetween() {
        for (int i = 0; i <= 10; i++) {
            DynamicFieldConditions dynamicFieldConditions = dynamicFieldConditions();
            List<Where> wheres = new ArrayList<>(2);
            wheres.add(Where.builder().addWhereCondition(new WhereCondition("id", WhereOperator.notBetween, "2,3")));
            dynamicFieldConditions.setWheres(wheres);
            DynamicColumnConditions dynamicColumnConditions = ConditionUtils.convertToColumnConditions("testCode", dynamicFieldConditions);

            List<Map<String, Object>> list = dynamicColumnMapper.getDynamicList(dynamicColumnConditions);
            System.out.println(JsonUtils.toJsonString(list));
        }

    }
}