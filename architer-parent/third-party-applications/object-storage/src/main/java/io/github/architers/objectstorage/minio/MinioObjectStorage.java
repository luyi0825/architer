package io.github.architers.objectstorage.minio;

import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.objectstorage.ObjectStorage;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * minio对象存储操作
 *
 * @author luyi
 */
@Slf4j
public class MinioObjectStorage implements ObjectStorage {

    private final MinioProperties minioProperties;

    private final MinioClient client;

    public MinioObjectStorage(MinioClient client, MinioProperties minioProperties) {
        this.client = client;
        this.minioProperties = minioProperties;
    }


    @Override
    public PutFileResponse putObject(File file, String key) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return putObject(fileInputStream, key);
        } catch (Exception e) {
            log.error("上传文件到minio失败", e);
            return PutFileResponse.fail().setKey(key).setErrorMessage(e.getMessage());
        }
    }

    @Override
    public PutFileResponse putObject(InputStream inputStream, String key) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(minioProperties.getDefaultBucket())
                    .object(key)
                    .build();
            ObjectWriteResponse writeResponse = client.putObject(putObjectArgs);
            return PutFileResponse.success().setKey(key)
                    .seteTag(writeResponse.etag())
                    .setVersionId(writeResponse.versionId())
                    .setUrl(key);
        } catch (Exception e) {
            log.error("上传文件到minio失败", e);
            return PutFileResponse.fail().setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void getObject(OutputStream outputStream, String key) throws Exception {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(minioProperties.getDefaultBucket())
                .object(key).build();
        GetObjectResponse getObjectResponse = client.getObject(getObjectArgs);
        getObjectResponse.transferTo(bufferedOutputStream);
    }

    @Override
    public boolean deleteObject(String key) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(minioProperties.getDefaultBucket()).object(key).build();
            client.removeObject(removeObjectArgs);
            return true;
        } catch (Exception e) {
            log.error("删除minio文件失败:{}", key, e);
        }
        return false;
    }
}
