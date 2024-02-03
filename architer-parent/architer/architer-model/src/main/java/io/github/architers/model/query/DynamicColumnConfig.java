package io.github.architers.model.query;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 动态条件配置
 *
 * @author luyi
 * @since 1.0.3
 */
@Data
public class DynamicColumnConfig implements Serializable {

    /**
     * 字段名(页面展示)
     */
    private String fieldName;

    /**
     * 列名（查询数据库的列）
     */
    private String columnName;

    /**
     * 字段别名（查询数据字段列后的别名）
     */
    private String columnAlias;


    /**
     * 得到实际的查询列的别名
     */
    public String getRealColumnAlias() {
        String alias;
        if (StringUtils.hasText(columnAlias)) {
            alias = columnAlias;
        } else {
            alias = fieldName;
        }
        alias = "'" + alias + "'";
        return alias;

    }

}
