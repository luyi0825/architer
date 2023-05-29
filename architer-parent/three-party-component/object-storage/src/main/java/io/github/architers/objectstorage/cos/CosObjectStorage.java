package io.github.architers.objectstorage.cos;

import com.qcloud.cos.COS;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import io.github.architers.objectstorage.PutFileResponse;
import io.github.architers.objectstorage.ObjectStorage;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 腾讯云对象存储
 *
 * @author luyi
 */
public class CosObjectStorage implements ObjectStorage {

    private final CosProperties cosProperties;
    private final COS cos;

    public CosObjectStorage(COS cos, CosProperties cosProperties) {
        this.cosProperties = cosProperties;
        this.cos = cos;
    }



    @Override
    public PutFileResponse putObject(File file, String key) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getDefaultBucket(), key, file);
        PutObjectResult putObjectResult = cos.putObject(putObjectRequest);
        return PutFileResponse.success().seteTag(putObjectResult.getETag()).setKey(key).setVersionId(putObjectResult.getVersionId())
                .setUrl(cosProperties.getEndpoint() + "/" + key);
    }

    @Override
    public PutFileResponse putObject(InputStream inputStream, String key) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getDefaultBucket(), key, inputStream,null);
        PutObjectResult putObjectResult = cos.putObject(putObjectRequest);
        return PutFileResponse.success().seteTag(putObjectResult.getETag()).setKey(key).setVersionId(putObjectResult.getVersionId())
                .setUrl(cosProperties.getEndpoint() + "/" + key);
    }

    @Override
    public InputStream getObjectInputStream(String key) throws Exception {
        return null;
    }



    @Override
    public boolean deleteObject(String key) {
        return false;
    }
}
