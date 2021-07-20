package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.es.consumer.entity.SyncResult;
import com.architecture.ultimate.es.model.dto.BaseSyncDocumentDTO;
import org.springframework.stereotype.Component;

/**
 * 回调处理
 *
 * @author luyi
 */
@Component
public class CallBackHandler {

    public void callBack(BaseSyncDocumentDTO syncDocumentDTO, SyncResult syncResult) {

    }

}
