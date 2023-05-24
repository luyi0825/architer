package io.github.architers.server.file.model.dto;

import io.github.architers.context.autocode.BaseEntity;
import io.github.architers.context.valid.group.AddGroup;
import io.github.architers.context.valid.group.EditGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author luyi
 */
@Data
public class TemplateCatalogDTO extends BaseEntity {
    /**
     * 模板目录ID
     */
    @NotNull(message = "模板目录ID不能为空", groups = EditGroup.class)
    private Integer id;
    /**
     * 目录名称
     */
    @NotBlank(message = "模板目录名称为空", groups = {AddGroup.class, EditGroup.class})
    private String catalogCaption;

    /**
     * 父级ID，一级目录就传0
     */
    @NotNull(message = "父模板目录不能为空", groups = {AddGroup.class, EditGroup.class})
    private Integer parentId;

    /**
     * 备注
     */
    @Length(max = 50, message = "备注不能超过50个字符", groups = {AddGroup.class, EditGroup.class})
    private String remark;
}
