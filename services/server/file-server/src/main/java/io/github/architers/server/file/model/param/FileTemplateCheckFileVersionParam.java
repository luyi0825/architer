package io.github.architers.server.file.model.param;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FileTemplateCheckFileVersionParam {
    /**
     * 是否开启版本校验
     */
    @NotNull(message = "是否校验模板不能为空")
    private Boolean enableCheck;
}
