package io.github.architers.model.tree;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * 树节点工具类
 *
 * @author luyi
 * @since 1.0.3
 */
public class NodeTreeUtils {


    public static <T> List<TreeNode> convertToTree(List<T> list, String parentField, Function<T, TreeNode> consumer) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<TreeNode> treeNodes = parent(list, parentField, consumer);
        for (TreeNode treeNode : treeNodes) {
            fillChildNodes(treeNode, parentField, list, consumer);
        }
        return treeNodes;

    }


    private static <T> void fillChildNodes(TreeNode parentNode,
                                           String parentField,
                                           List<T> objects,
                                           Function<T, TreeNode> consumer) {
        List<TreeNode> treeNodes = new LinkedList<>();
        for (T object : objects) {
            if (parentNode.getCode().equals(getParentCode(object, parentField))) {
                treeNodes.add(consumer.apply(object));
            }
        }
        parentNode.setChildrenNodes(treeNodes);
        //递归查询编码
        if (!treeNodes.isEmpty()) {
            for (TreeNode treeNode : treeNodes) {
                fillChildNodes(treeNode, parentField, objects, consumer);
            }
        }
    }

    private static String getParentCode(Object object, String parentField) {
        Object parentCode = getFieldValue(object, parentField);
        if (parentCode == null) {
            return null;
        }
        return parentCode.toString();
    }

    /**
     * 查询第一级
     */
    public static <T> List<TreeNode> parent(List<T> objects,
                                            String parentField,
                                            Function<T, TreeNode> consumer) {
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
                }
                continue;
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
