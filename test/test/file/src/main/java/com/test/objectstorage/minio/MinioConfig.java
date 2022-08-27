package com.test.objectstorage.minio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.objectstorage.ObjectStorageProperties;
import com.test.objectstorage.ObjectStorageType;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * minio配置类
 *
 * @author luyi
 */
@ConditionalOnClass(MinioClient.class)
@ConditionalOnProperty(prefix = ObjectStorageProperties.PREFIX + ".minio", name = "enabled", havingValue = "true")
@Configuration
@Slf4j
public class MinioConfig {
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnMissingBean(MinioClient.class)
    public MinioClient minioClient(ObjectStorageProperties objectStorageProperties) {
        MinioProperties minioProperties = objectStorageProperties.getMinio();
        Assert.isTrue(StringUtils.hasText(minioProperties.getEndpoint()), "minio endpoint is null");
        Assert.isTrue(StringUtils.hasText(minioProperties.getAccessKey()), "minio accessKey is null");
        Assert.isTrue(StringUtils.hasText(minioProperties.getSecretKey()), "minio secretKey is null");
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

    @Bean(ObjectStorageType.MINIO)
    @ConditionalOnBean(MinioClient.class)
    public MinioObjectStorage minioFileService(MinioClient minioClient, ObjectStorageProperties objectStorageProperties) throws JsonProcessingException {
        log.info("初始化minioObjectStorage:{}", objectMapper.writeValueAsString(objectStorageProperties.getMinio()));
        return new MinioObjectStorage(minioClient, objectStorageProperties.getMinio());
    }
}
