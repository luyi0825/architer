package io.github.architers.server.file.domain.entity;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author luyi
 */
@Data
@Valid
public class CheckInfo {


    /**
     * 检验版本
     */
    @Valid
    @NotNull(message = "校验版本信息不能为空")
    private FileVersion fileVersion;

    /**
     * 校验的行信息
     */
    @Valid
    @NotNull(message = "校验行信息不能为空")
    private RowInfo rowInfo;

    @Data
    public static class FileVersion {
        /**
         * 是否开启版本校验
         */
        @NotNull(message = "是否校验模板不能为空")
        private Boolean enableCheck;

        /**
         * 版本
         */
        private String version;
    }


    /**
     * 检验行信息
     */
    @Data
    public static class RowInfo {
        /**
         * 是否开启行校验
         */
        @NotNull(message = "是否开启行校验不能为空")
        private Boolean enableCheck;
        private Integer startRow;
        private Integer endRow;
    }


}
