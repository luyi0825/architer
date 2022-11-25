package io.github.architers.server.file.domain.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 执行任务参数
 *
 * @author luyi
 */
@Data
public class FileTaskParam<T> {


    /**
     * 文件任务主键ID
     */
    private Long recordId;

    /**
     * 任务编码
     */
    @NotBlank(message = "任务编码不能为空")
    private String taskCode;

    /**
     * 执行参数
     */
    @NotNull(message = "执行参数不能为空")
    @Valid
    private T taskParam;


}
