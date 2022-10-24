package io.github.architers.syscenter.user.utils;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class NodeTreeUtils {


    @Data
    public static class TreeNode implements Serializable {

        private String code;
        private String caption;
        private String parentCode;
        private Object data;
        private List<TreeNode> childrenNodes;


    }

    public static <T> List<TreeNode> convertToTree(List<T> objects, String parentField, Function<T,
            TreeNode> consumer) {
        if (CollectionUtils.isEmpty(objects)) {
            return null;
        }
        List<TreeNode> treeNodes = parent(objects, parentField, consumer);
        for (TreeNode treeNode : treeNodes) {
            fillChildNodes(treeNode, parentField, objects, consumer);
        }
        return treeNodes;

    }


    private static <T> void fillChildNodes(TreeNode parentNode, String parentField, List<T> objects,
                                           Function<T, TreeNode> consumer) {
        List<TreeNode> treeNodes = new LinkedList<>();
        for (T object : objects) {
            if (parentNode.getCode().equals(getFieldValue(object, parentField))) {
                treeNodes.add(consumer.apply(object));
            }
        }
        parentNode.setChildrenNodes(treeNodes);
        //递归查询编码
        if (treeNodes.size() > 0) {
            for (TreeNode treeNode : treeNodes) {
                fillChildNodes(treeNode, parentField, objects, consumer);
            }
        }
    }

    /**
     * 查询第一级
     */
    public static <T> List<TreeNode> parent(List<T> objects,
                                            String parentField, Function<T, TreeNode> consumer) {
        List<TreeNode> treeNodes = new LinkedList<>();
        for (T object : objects) {
            Object value = getFieldValue(object, parentField);
            //为空表示父级
            if (value == null) {
                TreeNode treeNode = consumer.apply(object);
                treeNodes.add(treeNode);
                continue;
            }
            //数值为0也表示父级
            if (value instanceof Number) {
                long parentId = ((Number) value).longValue();
                if (parentId == 0L) {
                    TreeNode treeNode = consumer.apply(object);
                    treeNodes.add(treeNode);
                    continue;
                }
            }
            //空字符串也表示第一级
            if (StringUtils.hasText(value.toString())) {
                TreeNode treeNode = consumer.apply(object);
                treeNodes.add(treeNode);
            }
        }
        return treeNodes;
    }

    private static Object getFieldValue(Object object, String fieldName) {

        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
