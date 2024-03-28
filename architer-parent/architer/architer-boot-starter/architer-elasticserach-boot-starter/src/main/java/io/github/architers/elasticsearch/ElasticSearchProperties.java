package io.github.architers.elasticsearch;

import lombok.Data;

import java.io.Serializable;

/**
 * ElasticSearch属性配置
 *
 * @author luyi
 */
@Data
public class ElasticSearchProperties implements Serializable {

    /**
     * es节点信息
     */
    private String nodes;

    /**
     * 秘钥
     */
    private String apiKey;

}
