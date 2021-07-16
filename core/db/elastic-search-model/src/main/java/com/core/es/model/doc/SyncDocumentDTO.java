package com.core.es.model.doc;

import java.io.Serializable;
import java.util.List;

/**
 * @author luyi
 * document的请求数据-同步数据用
 */
public class SyncDocumentDTO implements Serializable {
    /**
     * 是否加锁，数据强一直性加锁处理--存在并发修改数据的时候才加
     * 会影响一定的效率,不为空的时候会加锁
     */
    private String lock;
    /**
     * 版本，用于控制顺序,当加锁的时候，高版本的doc操作后，将放弃低版本的数据
     */
    private Long version;
    private List<DocumentRequest> docs;

    @Override
    public String toString() {
        return super.toString();
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public List<DocumentRequest> getDocs() {
        return docs;
    }

    public void setDocs(List<DocumentRequest> docs) {
        this.docs = docs;
    }
}
