package io.github.architers.objectstorage.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.architers.objectstorage.ObjectStorageProperties;
import io.github.architers.objectstorage.ObjectStorageType;
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
 * 阿里云oss配置类
 *
 * @author luyi
 */
@Configuration
@ConditionalOnClass(OSS.class)
@ConditionalOnProperty(prefix = ObjectStorageProperties.PREFIX + ".oss", name = "enabled", havingValue = "true")
@Slf4j
public class OssConfig {

    @Resource
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnMissingBean(OSS.class)
    public OSS ossClient(ObjectStorageProperties objectStorageProperties) {
        OssProperties ossProperties = objectStorageProperties.getOss();
        Assert.isTrue(StringUtils.hasText(ossProperties.getEndpoint()), "oss endpoint is null");
        Assert.isTrue(StringUtils.hasText(ossProperties.getAccessKey()), "oss accessKey is null");
        Assert.isTrue(StringUtils.hasText(ossProperties.getSecretKey()), "oss secretKey is null");
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
    }

    @Bean(ObjectStorageType.OSS)
    @ConditionalOnBean(OSS.class)
    public AliCloudOssObjectStorage aliCloudOssFileStorage(OSS ossClient, ObjectStorageProperties objectStorageProperties) throws JsonProcessingException {
        log.info("初始化ossObjectStorage:{}", objectMapper.writeValueAsString(objectStorageProperties.getOss()));
        return new AliCloudOssObjectStorage(ossClient, objectStorageProperties.getOss());
    }
}
