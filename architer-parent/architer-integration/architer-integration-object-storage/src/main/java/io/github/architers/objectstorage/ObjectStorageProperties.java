package io.github.architers.objectstorage;

import io.github.architers.objectstorage.cos.CosProperties;
import io.github.architers.objectstorage.minio.MinioProperties;
import io.github.architers.objectstorage.oss.OssProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author luyi
 * 文件配置
 */
@ConfigurationProperties(prefix = ObjectStorageProperties.PREFIX)
public class ObjectStorageProperties implements Serializable {

    public static final String PREFIX = "architers.object-storage";

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

    public MinioProperties getMinio() {
        return minio;
    }

    public void setMinio(MinioProperties minio) {
        this.minio = minio;
    }

    public OssProperties getOss() {
        return oss;
    }

    public void setOss(OssProperties oss) {
        this.oss = oss;
    }

    public CosProperties getCos() {
        return cos;
    }

    public void setCos(CosProperties cos) {
        this.cos = cos;
    }
}
