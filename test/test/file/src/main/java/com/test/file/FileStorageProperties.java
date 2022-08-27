package com.test.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 * 文件配置
 */
@Data
@ConfigurationProperties(
        prefix = FileStorageProperties.PREFIX,
        ignoreUnknownFields = true
)
public class FileStorageProperties {

    public static final String PREFIX = "architecture.file-storage";

    /**
     * minio配置
     */
    private MinioProperties minio;

    /**
     * 阿里云oss
     */
    private OssProperties oss;


}
