package io.github.architers.objectstorage;

import io.github.architers.objectstorage.cos.CosConfig;
import io.github.architers.objectstorage.minio.MinioConfig;
import io.github.architers.objectstorage.oss.OssConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @author luyi
 * 文件配置类
 */
@Configuration
@EnableConfigurationProperties(ObjectStorageProperties.class)
@Import({CosConfig.class, OssConfig.class, MinioConfig.class})
public class ObjectStorageConfig {


   public ObjectStorageConfig( ObjectStorageProperties objectStorageProperties){
       System.out.println(objectStorageProperties);
   }






}
