package io.github.architers.model.tree;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 树节点
 *
 * @author luyi
 */
@Data
public class TreeNode {

    /**
     * 节点ID编码
     */
    private String code;
    /**
     * 名称
     */
    private String caption;
    /**
     * 父节点编码
     */

    private String parentCode;
    /**
     * 子节点信息
     */

    private List<TreeNode> childrenNodes;

    /**
     * 其他信息-用于拓展
     */
    private Map<String, Object> extra;
}
