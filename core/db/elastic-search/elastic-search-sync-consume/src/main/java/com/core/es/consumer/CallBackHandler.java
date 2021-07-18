package com.core.es.consumer;

import com.core.es.consumer.entiry.SyncResult;
import com.core.es.consumer.service.SyncResultService;
import com.core.es.model.dto.BaseSyncDocumentDTO;
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
