package io.github.architers.server.file.eums;

import lombok.Getter;

@Getter
public enum FileContentType {

    xls("application/vnd.ms-excel", ".xls"),
    xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");
    /**
     * 文件类型
     */
    private final String contentType;

    /**
     * 文件后缀
     */
    private final String fileSuffix;

    FileContentType(String contentType, String fileSuffix) {
        this.contentType = contentType;
        this.fileSuffix = fileSuffix;
    }
}
