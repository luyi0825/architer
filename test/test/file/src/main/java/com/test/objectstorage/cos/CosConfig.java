package com.test.objectstorage.cos;

import com.aliyun.oss.OSS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qcloud.cos.COS;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import com.test.objectstorage.ObjectStorageProperties;
import com.test.objectstorage.ObjectStorageType;
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
 * 腾讯云cos配置类
 *
 * @author luyi
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OSS.class)
@ConditionalOnProperty(prefix = ObjectStorageProperties.PREFIX + ".cos", name = "enabled", havingValue = "true")
@Slf4j
public class CosConfig {
    @Resource
    private ObjectMapper objectMapper;

    @Bean
    @ConditionalOnMissingBean(COS.class)
    public COS cosClient(ObjectStorageProperties objectStorageProperties) {
        CosProperties cosProperties = objectStorageProperties.getCos();
        Assert.isTrue(StringUtils.hasText(cosProperties.getEndpoint()), "oss endpoint is null");
        Assert.isTrue(StringUtils.hasText(cosProperties.getAccessKey()), "oss accessKey is null");
        Assert.isTrue(StringUtils.hasText(cosProperties.getSecretKey()), "oss secretKey is null");

        COSCredentials cred = new BasicCOSCredentials(cosProperties.getAccessKey(), cosProperties.getSecretKey());
        // 2 设置bucket的区域,
        ClientConfig clientConfig = new ClientConfig(new Region(cosProperties.getRegion()));
        // 3 生成cos客户端
        return new COSClient(cred, clientConfig);
    }

    @Bean(ObjectStorageType.COS)
    @ConditionalOnBean(COS.class)
    public CosObjectStorage cosObjectStorage(COS cos, ObjectStorageProperties objectStorageProperties) throws JsonProcessingException {
        log.info("初始化cosObjectStorage:{}", objectMapper.writeValueAsString(objectStorageProperties.getCos()));
        return new CosObjectStorage(cos, objectStorageProperties.getCos());
    }
}
