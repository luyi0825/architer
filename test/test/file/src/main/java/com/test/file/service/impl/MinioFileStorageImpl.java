package com.test.file.service.impl;

import com.test.file.FileStorageConfig;
import com.test.file.MinioProperties;
import com.test.file.PutFileResponse;
import com.test.file.service.FileStorage;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * monio文件操作
 *
 * @author luyi
 */
@Slf4j
public class MinioFileStorageImpl implements FileStorage {

    private final MinioProperties minioProperties;

    private final MinioClient client;

    public MinioFileStorageImpl(MinioClient client, MinioProperties minioProperties) {
        this.client = client;
        this.minioProperties = minioProperties;
    }


    @Override
    public PutFileResponse putFile(File file, String key) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder().stream(fileInputStream, file.length(), -1)
                    .bucket(minioProperties.getDefaultBucket()).build();
            ObjectWriteResponse writeResponse = client.putObject(putObjectArgs);
            return PutFileResponse.success().setKey(key).setVersionId(writeResponse.versionId())
                    .seteTag(writeResponse.etag());
        } catch (Exception e) {
            log.error("上传文件到minio失败", e);
            return PutFileResponse.fail().setKey(key).setErrorMessage(e.getMessage());
        }
    }

    @Override
    public PutFileResponse uploadFile(InputStream inputStream, String key) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getDefaultBucket())
                    .object(key)
                    .build();
            ObjectWriteResponse writeResponse = client.putObject(putObjectArgs);
            return PutFileResponse.success().setKey(key).seteTag(writeResponse.etag()).setVersionId(writeResponse.versionId());
        } catch (Exception e) {
            log.error("上传文件到minio失败", e);
            return PutFileResponse.fail().setErrorMessage(e.getMessage());
        }

    }

    @Override
    public void downloadFile(OutputStream outputStream, String key) throws Exception {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(minioProperties.getDefaultBucket())
                .object(key).build();
        GetObjectResponse getObjectResponse = client.getObject(getObjectArgs);
        getObjectResponse.transferTo(bufferedOutputStream);
    }

    @Override
    public boolean delete(String fileName) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(minioProperties.getDefaultBucket()).object(fileName).build();
            client.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            log.error("删除minio文件失败:{}", fileName, e);
        }
        return false;


    }
}
