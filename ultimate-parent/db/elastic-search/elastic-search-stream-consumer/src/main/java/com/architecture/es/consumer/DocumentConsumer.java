package com.architecture.es.consumer;

import com.architecture.es.dml.service.DocService;
import com.architecture.es.model.dto.BatchSyncDocumentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author luyi
 */
@Configuration
public class DocumentConsumer {

    private DocService docService;


    @Bean
    public Consumer<BatchSyncDocumentDTO> documentConsumer() {
        return batchSyncDocumentDTO -> {
            try {
                docService.bulk(batchSyncDocumentDTO.getDocs());
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
