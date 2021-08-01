package com.architecture.ultimate.es.consumer;


import com.architecture.ultimate.es.consumer.entity.SyncResult;
import com.architecture.ultimate.es.model.CallBackWay;
import com.architecture.ultimate.es.model.EsConstant;
import com.architecture.ultimate.es.model.dto.BaseSyncDocumentDTO;
import com.architecture.ultimate.module.common.StatusCode;
import com.architecture.ultimate.module.common.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * 回调处理
 *
 * @author luyi
 */
@Component
public class CallBackHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallBackHandler.class);


    private RestTemplate restTemplate;

    public void callBack(BaseSyncDocumentDTO syncDocumentDTO, SyncResult syncResult) {
        if (!syncResult.isSuccess()) {
            return;
        }
        String callBackWay = syncDocumentDTO.getCallBackWay();
        if (CallBackWay.URL.name().equals(callBackWay)) {
            Map<String, Object> callBackParams = syncDocumentDTO.getCallBackParams();
            String url = (String) callBackParams.get(EsConstant.CALL_BACK_URL);
            BaseResponse baseResponse = restTemplate.postForObject(url, callBackParams.get(EsConstant.CALL_BACK_PARAMS), BaseResponse.class);
            assert baseResponse != null;
            if (StatusCode.SUCCESS.getCode() == baseResponse.getCode()) {
                LOGGER.info("回调成功{}-{}", syncDocumentDTO.getBusinessKey(), syncDocumentDTO.getBatchId());
            }
        }
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
