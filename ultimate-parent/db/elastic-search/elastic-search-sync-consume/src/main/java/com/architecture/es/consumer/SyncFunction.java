package com.architecture.es.consumer;


import com.architecture.es.model.dto.BaseSyncDocumentDTO;

import java.io.IOException;

public interface SyncFunction {
    void handler(BaseSyncDocumentDTO baseSyncDocumentDTO) throws IOException;
}
