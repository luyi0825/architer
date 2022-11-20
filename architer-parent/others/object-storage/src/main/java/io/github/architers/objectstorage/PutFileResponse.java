package io.github.architers.objectstorage;


/**
 * @author luyi
 * putFile的返回结果
 */
public class PutFileResponse {

    /**
     * put的结果
     */
    private boolean result;

    /**
     * 上传失败的原因
     */
    private String errorMessage;
    /**
     * 文件标识key
     */
    private String key;
    /**
     * 标签
     */
    private String eTag;
    /**
     * 版本
     */
    private String versionId;

    /**
     * 访问的url
     */
    private String url;


    /**
     * 成功
     */
    public static PutFileResponse success() {
        PutFileResponse putFileResponse = new PutFileResponse();
        putFileResponse.setResult(Boolean.TRUE);
        return putFileResponse;
    }


    public String getUrl() {
        return url;
    }

    public PutFileResponse setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 失败
     */
    public static PutFileResponse fail() {
        PutFileResponse putFileResponse = new PutFileResponse();
        putFileResponse.setResult(Boolean.FALSE);
        return putFileResponse;
    }


    public boolean isResult() {
        return result;
    }

    public PutFileResponse setResult(boolean result) {
        this.result = result;
        return this;
    }

    public String getKey() {
        return key;
    }

    public PutFileResponse setKey(String key) {
        this.key = key;
        return this;
    }

    public String geteTag() {
        return eTag;
    }

    public PutFileResponse seteTag(String eTag) {
        this.eTag = eTag;
        return this;
    }

    public String getVersionId() {
        return versionId;
    }

    public PutFileResponse setVersionId(String versionId) {
        this.versionId = versionId;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public PutFileResponse setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }
}
