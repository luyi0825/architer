package com.test.objectstorage;

import com.test.objectstorage.cos.CosProperties;
import com.test.objectstorage.minio.MinioProperties;
import com.test.objectstorage.oss.OssProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author luyi
 * 文件配置
 */
@Data
@ConfigurationProperties(
        prefix = ObjectStorageProperties.PREFIX,
        ignoreUnknownFields = true
)
public class ObjectStorageProperties {

    public static final String PREFIX = "architecture.object-storage";

    /**
     * minio配置
     */
    private MinioProperties minio;

    /**
     * 阿里云oss
     */
    private OssProperties oss;

    /**
     * 腾讯云cos
     */
    private CosProperties cos;


}
