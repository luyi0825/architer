package io.github.architers.server.file.eums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件类型
 *
 * @author luyi
 */
@Getter
public enum FileType {

    xlsx("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");


    /**
     * 文件类型
     */
    private String contentType;
    /**
     * 后缀
     */
    private String suffix;

    FileType(String contentType, String suffix) {
        this.contentType = contentType;
        this.suffix = suffix;
    }


    public static Map<String, FileType> convertToContentTypeMap() {
        Map<String, FileType> contentTypeMap = new HashMap<>(FileType.values().length, 1);
        for (FileType value : FileType.values()) {
            contentTypeMap.put(value.getContentType(), value);
        }
        return contentTypeMap;
    }


}
