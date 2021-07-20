package com.architecture.ultimate.es.sync;

import cn.hutool.core.exceptions.ValidateException;

import com.architecture.ultimate.es.model.doc.DocumentRequest;
import com.architecture.ultimate.es.model.dto.BaseSyncDocumentDTO;
import com.architecture.ultimate.es.model.dto.BatchSyncDocumentDTO;
import com.architecture.ultimate.es.model.dto.SyncDocumentDTO;
import org.apache.commons.lang3.ArrayUtils;
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
            throw new ValidateException("doc is null");
        }
        validRequestBody(documentRequest);
    }

    /**
     * 批量发送数据校验
     */
    public static void validBatchSyncDocumentDTO(BatchSyncDocumentDTO documentDTO) {
        validHead(documentDTO);
        if (CollectionUtils.isEmpty(documentDTO.getDocs())) {
            throw new ValidateException("docs size must >0");
        }
        documentDTO.getDocs().forEach(SenderValidUtils::validRequestBody);
    }

    /**
     * 校验头数据
     */
    private static void validHead(BaseSyncDocumentDTO documentDTO) {
        if (StringUtils.isEmpty(documentDTO.getBusinessKey())) {
            throw new ValidateException("business key is null");
        }
        if (StringUtils.isEmpty(documentDTO.getBatchId())) {
            throw new ValidateException("batch key is null");
        }
    }

    /**
     * 对request进行校验
     */
    private static void validRequestBody(DocumentRequest request) {
        if (request == null) {
            throw new ValidateException("doc");
        }
        if (StringUtils.isEmpty(request.getIndex())) {
            throw new ValidateException("index is null");
        }
        if (StringUtils.isEmpty(request.getIndex())) {
            throw new ValidateException("id is null");
        }
        if (CollectionUtils.isEmpty(request.getSource())) {
            throw new ValidateException("source is null");
        }
        if (StringUtils.isEmpty(request.getRequestType())) {
            throw new ValidateException("requestType is null");
        }
    }
}
