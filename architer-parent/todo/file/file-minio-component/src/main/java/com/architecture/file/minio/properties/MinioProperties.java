package io.github.architers.file.minio.properties;

/**
 * @author luyi
 * minio属性配置
 */
public class MinioProperties {

    private String endpoint;
    private String accessKey;
    private String secretKey;
    private String bucketNameHtml;
    private String bucketNameImage;


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketNameHtml() {
        return bucketNameHtml;
    }

    public void setBucketNameHtml(String bucketNameHtml) {
        this.bucketNameHtml = bucketNameHtml;
    }

    public String getBucketNameImage() {
        return bucketNameImage;
    }

    public void setBucketNameImage(String bucketNameImage) {
        this.bucketNameImage = bucketNameImage;
    }
}
