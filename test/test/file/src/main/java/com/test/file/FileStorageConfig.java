package com.test.file;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.test.file.service.impl.MinioFileStorageImpl;
import com.test.file.service.impl.OssFileStorageImpl;
import io.minio.MinioClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @author luyi
 * 文件配置类
 */
@Configuration
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageConfig {

    @Resource
    private FileStorageProperties fileStorageProperties;

    @Bean
    @ConditionalOnClass(MinioClient.class)
    @ConditionalOnProperty(prefix = FileStorageProperties.PREFIX + ".minio", name = "enabled", havingValue = "true")
    public MinioClient minioClient(FileStorageProperties fileStorageProperties) {
        MinioProperties minioProperties = fileStorageProperties.getMinio();
        Assert.isTrue(StringUtils.hasText(minioProperties.getEndpoint()), "minio endpoint is null");
        Assert.isTrue(StringUtils.hasText(minioProperties.getAccessKey()), "minio accessKey is null");
        Assert.isTrue(StringUtils.hasText(minioProperties.getSecretKey()), "minio secretKey is null");
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }

    @Bean
    @ConditionalOnBean(MinioClient.class)
    public MinioFileStorageImpl minioFileService(MinioClient minioClient) {
        return new MinioFileStorageImpl(minioClient, fileStorageProperties.getMinio());
    }


    @Bean
   // @ConditionalOnClass(OSS.class)
   // @ConditionalOnProperty(prefix = FileStorageProperties.PREFIX + ".oss", name = "enabled", havingValue = "true")
    public OSS ossClient(FileStorageProperties fileStorageProperties) {
        OssProperties ossProperties = fileStorageProperties.getOss();
        Assert.isTrue(StringUtils.hasText(ossProperties.getEndpoint()), "oss endpoint is null");
        Assert.isTrue(StringUtils.hasText(ossProperties.getAccessKey()), "oss accessKey is null");
        Assert.isTrue(StringUtils.hasText(ossProperties.getSecretKey()), "oss secretKey is null");
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
    }

    @Bean
    @ConditionalOnBean(OSS.class)
    public OssFileStorageImpl OssFileStorageImpl(OSS ossClient) {
        return new OssFileStorageImpl(ossClient, fileStorageProperties.getOss());
    }
}
