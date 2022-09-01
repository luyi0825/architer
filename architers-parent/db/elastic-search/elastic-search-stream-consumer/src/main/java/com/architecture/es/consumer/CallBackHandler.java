package io.github.architers.es.consumer;


import io.github.architers.context.ResponseStatusEnum;
import io.github.architers.context.response.ResponseResult;
import io.github.architers.es.consumer.entity.SyncResult;
import io.github.architers.es.model.CallBackWay;
import io.github.architers.es.model.EsConstant;
import io.github.architers.es.model.dto.BaseSyncDocumentDTO;
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
            ResponseResult baseResponse = restTemplate.postForObject(url, callBackParams.get(EsConstant.CALL_BACK_PARAMS), ResponseResult.class);
            assert baseResponse != null;
            if (ResponseStatusEnum.SUCCESS.getCode() == baseResponse.getCode()) {
                LOGGER.info("回调成功{}-{}", syncDocumentDTO.getBusinessKey(), syncDocumentDTO.getBatchId());
            }
        }
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
