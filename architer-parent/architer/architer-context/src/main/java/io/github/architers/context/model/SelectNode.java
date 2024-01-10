package io.github.architers.context.model;

import lombok.Data;

import java.util.Map;

/**
 * 下拉节点
 *
 * @author luyi
 */
@Data
public class SelectNode {

    /**
     * 节点ID编码
     */
    private String code;
    /**
     * 名称
     */
    private String caption;

    /**
     * 其他信息-用于拓展
     */
    private Map<String, Object> extra;
}
