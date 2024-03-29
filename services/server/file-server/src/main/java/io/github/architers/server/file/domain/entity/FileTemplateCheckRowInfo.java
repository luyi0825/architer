package io.github.architers.server.file.domain.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 文件模板校验行信息
 *
 * @author luyi
 */
@Data
public class FileTemplateCheckRowInfo implements Serializable {
    /**
     * 是否开启行校验
     */
    @NotNull(message = "是否开启行校验不能为空")
    private Boolean enableCheck;
    private Integer startRow;
    private Integer endRow;
    private String base64RowStr;

}
