package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.JdbcType;

/**
 * 文件模板
 *
 * @author luyi
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName( autoResultMap = true)
public class FileTemplate extends BaseEntity {
    /**
     * 模板ID
     */
    private Long id;

    /**
     * 目录ID
     */
    private Long catalogId;

    /**
     * 模板编码
     */
    private String templateCode;

    /**
     * 模板名称
     */
    private String templateCaption;


    /**
     * 校验行信息
     */
    @TableField( jdbcType = JdbcType.VARCHAR,  typeHandler = JacksonTypeHandler.class)
    private FileTemplateCheckRowInfo checkRowInfo;


    /**
     * 模板url
     */
    private String templateUrl;

    /**
     * 模板key
     */
    private String templateKey;
    /**
     * 描述
     */
    private String remark;
}
