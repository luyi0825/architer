package com.core.es.consumer;

import com.core.es.model.dto.BaseSyncDocumentDTO;

import java.io.IOException;

public interface SyncFunction {
    void handler(BaseSyncDocumentDTO baseSyncDocumentDTO) throws IOException;
}
