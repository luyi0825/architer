package io.github.architers.server.file.eums;

import io.github.architers.context.exception.BusErrorException;
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

    public static String getFileSuffixByContentType(String contentType) {
        for (FileContentType value : FileContentType.values()) {
            if (value.getContentType().equals(contentType)) {
                return value.getFileSuffix();
            }
        }
        throw new BusErrorException("文件类型暂时不支持");
    }
}
