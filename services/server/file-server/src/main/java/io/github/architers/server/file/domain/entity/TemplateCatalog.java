package io.github.architers.server.file.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.architers.context.autocode.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luyi
 * 导入模板目录
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("file_template_catalog")
public class TemplateCatalog extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 目录名称
     */
    private String catalogCaption;

    /**
     * 路径
     */
    private String savePath;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 备注
     */
    private String remark;
}
