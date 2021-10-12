package com.architecture.es.dataquery;

import com.architecture.es.dml.service.DocService;
import com.architecture.es.model.doc.DocumentResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luyi
 * index的对应关系
 */
public class DbIndexFatory {

    private DocService docService;

    private String index = "t_config_index_relation";
    /**
     * 数据库的index
     */
    private String dbIndex = "dbIndex";
    /**
     * 暴露给接口的index
     */
    private String apiIndex = "apiIndex";

    public String getDbIndex(String apiIndex) throws IOException {
        List<DocumentResponse> responses = docService.queryList(index);
        Map<String, String> map = new HashMap<>();
        responses.forEach(documentResponse -> {
            map.put(documentResponse.get(apiIndex).toString(), documentResponse.get(dbIndex).toString());
        });
        String dbIndex = map.get(apiIndex);
        if (!StringUtils.hasText(dbIndex)) {
            throw new RuntimeException("apiIndex有误");
        }
        return dbIndex;
    }
}
