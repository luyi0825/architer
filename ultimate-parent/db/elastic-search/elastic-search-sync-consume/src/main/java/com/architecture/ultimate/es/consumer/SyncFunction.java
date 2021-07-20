package com.architecture.ultimate.es.consumer;



import com.architecture.ultimate.es.model.dto.BaseSyncDocumentDTO;

import java.io.IOException;

public interface SyncFunction {
    void handler(BaseSyncDocumentDTO baseSyncDocumentDTO) throws IOException;
}
