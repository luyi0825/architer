package com.test.objectstorage;

import com.test.objectstorage.cos.CosConfig;
import com.test.objectstorage.minio.MinioConfig;
import com.test.objectstorage.oss.OssConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author luyi
 * 文件配置类
 */
@Configuration
@EnableConfigurationProperties(ObjectStorageProperties.class)
@Import({CosConfig.class, OssConfig.class, MinioConfig.class})
public class ObjectStorageConfig {


}
