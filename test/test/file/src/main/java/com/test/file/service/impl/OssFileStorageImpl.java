package com.test.file.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.test.file.FileStorageProperties;
import com.test.file.OssProperties;
import com.test.file.PutFileResponse;
import com.test.file.service.FileStorage;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 阿里云oss文件存储
 *
 * @author luyi
 */
@Slf4j
public class OssFileStorageImpl implements FileStorage {

    private final OSS ossClient;

    private final OssProperties ossProperties;

    public OssFileStorageImpl(OSS client, OssProperties ossProperties) {
        this.ossClient = client;
        this.ossProperties = ossProperties;
    }

    @Override
    public PutFileResponse putFile(File file, String key) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getDefaultBucket(), key, file);
        return putObject(putObjectRequest, key);

    }

    @Override
    public PutFileResponse uploadFile(InputStream inputStream, String key) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getDefaultBucket(), key, inputStream);
        return putObject(putObjectRequest, key);
    }


    private PutFileResponse putObject(PutObjectRequest putObjectRequest, String key) {
        try {
            PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
            if (putObjectResult.getResponse() != null && !putObjectResult.getResponse().isSuccessful()) {
                //上传失败
                return PutFileResponse.fail().setErrorMessage(putObjectResult.getResponse().getErrorResponseAsString());
            }
            return PutFileResponse.success().seteTag(putObjectResult.getETag()).setVersionId(putObjectResult.getVersionId())
                    .setKey(key).setUrl("https://0825test.oss-cn-hangzhou.aliyuncs.com/" + key);
        } catch (Exception e) {
            return PutFileResponse.fail().setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void downloadFile(OutputStream outputStream, String key) throws Exception {
        GetObjectRequest getObjectRequest = new GetObjectRequest(ossProperties.getDefaultBucket(), key);
        OSSObject ossObject = ossClient.getObject(getObjectRequest);
        try (InputStream inputStream = ossObject.getObjectContent()) {
            inputStream.transferTo(outputStream);
        }
    }


    @Override
    public boolean delete(String key) {
        GenericRequest genericRequest = new GenericRequest(ossProperties.getDefaultBucket(), key);
        VoidResult ossObject = ossClient.deleteObject(genericRequest);
        log.info("oss删除文件：{}，返回状态：{}", key, ossObject.getResponse().getStatusCode());
        return ossObject.getResponse().isSuccessful();
    }
}
