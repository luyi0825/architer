package io.github.architers.server.file.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 文件模板校验行信息
 *
 * @author luyi
 */
@Data
public class FileTemplateCheckRowInfoParams {
    /**
     * 是否开启行校验
     */
    @NotNull(message = "是否开启行校验不能为空")
    private Boolean enableCheck;
    private Integer startRow;
    private Integer endRow;
}
