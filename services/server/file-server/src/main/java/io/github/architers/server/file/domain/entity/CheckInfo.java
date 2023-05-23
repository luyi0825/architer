package io.github.architers.server.file.domain.entity;

import lombok.Data;

/**
 * @author luyi
 */
public class CheckInfo {


    /**
     * 是否检验版本
     */
    private FileVersion fileVersion;

    /**
     * 校验的行信息
     */
    private RowInfo rowInfo;

    @Data
    public static class FileVersion {
        /**
         * 是否开启版本校验
         */
        private boolean enableCheck;

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
        private boolean enableCheck;
        private int startRow;
        private int endRow;
    }


}
