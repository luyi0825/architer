package io.github.architers.es.sync;


import io.github.architers.contenxt.exception.ServiceException;
import io.github.architers.es.model.doc.DocumentRequest;
import io.github.architers.es.model.dto.BaseSyncDocumentDTO;
import io.github.architers.es.model.dto.BatchSyncDocumentDTO;
import io.github.architers.es.model.dto.SyncDocumentDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author luyi
 * 发送校验器
 */
@Component
public class SenderValidUtils {

    /**
     * 单条数据发送校验
     */
    public static void validSyncDocumentDTO(SyncDocumentDTO documentDTO) {
        validHead(documentDTO);
        DocumentRequest documentRequest = documentDTO.getDoc();
        if (documentRequest == null) {
            throw new ServiceException("doc is null");
        }
        validRequestBody(documentRequest);
    }

    /**
     * 批量发送数据校验
     */
    public static void validBatchSyncDocumentDTO(BatchSyncDocumentDTO documentDTO) {
        validHead(documentDTO);
        if (CollectionUtils.isEmpty(documentDTO.getDocs())) {
            throw new ServiceException("docs size must >0");
        }
        documentDTO.getDocs().forEach(SenderValidUtils::validRequestBody);
    }

    /**
     * 校验头数据
     */
    private static void validHead(BaseSyncDocumentDTO documentDTO) {
        if (StringUtils.isEmpty(documentDTO.getBusinessKey())) {
            throw new ServiceException("business key is null");
        }
        if (StringUtils.isEmpty(documentDTO.getBatchId())) {
            throw new ServiceException("batch key is null");
        }
    }

    /**
     * 对request进行校验
     */
    private static void validRequestBody(DocumentRequest request) {
        if (request == null) {
            throw new ServiceException("doc");
        }
        if (StringUtils.isEmpty(request.getIndex())) {
            throw new ServiceException("index is null");
        }
        if (StringUtils.isEmpty(request.getIndex())) {
            throw new ServiceException("id is null");
        }
        if (CollectionUtils.isEmpty(request.getSource())) {
            throw new ServiceException("source is null");
        }
        if (StringUtils.isEmpty(request.getRequestType())) {
            throw new ServiceException("requestType is null");
        }
    }
}
