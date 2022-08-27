package com.test.objectstorage;

/**
 * 对象存储bean名称
 *
 * @author luyi
 */
public interface ObjectStorageType {

    /**
     * 腾讯云cos
     */
    String COS = "cosObjectStorage";
    /**
     * 阿里oss
     */
    String OSS = "ossObjectStorage";
    /**
     * minio
     */
    String MINIO = "minioObjectStorage";


}
