package io.github.architers.server.file.model.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FileTemplateCheckFileVersion implements Serializable {
    /**
     * 是否开启版本校验
     */
    @NotNull(message = "是否校验模板不能为空")
    private Boolean enableCheck;

    /**
     * 文件版本
     */
    private String fileVersion;
}
