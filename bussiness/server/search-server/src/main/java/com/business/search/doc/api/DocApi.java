package com.business.search.doc.api;

import com.business.search.doc.model.DocumentRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author luyi
 */
@RestController
@RequestMapping("/docApi")
public interface DocApi {

    /**
     * 添加文档
     *
     * @param index 索引信息
     * @param documentRequest  文档信息(数据)
     */
    @PostMapping("/put/{index}")
    void put(@PathVariable(name = "index") String index, @RequestBody DocumentRequest documentRequest) throws IOException;


    /**
     * 查询集合
     *
     * @param index 索引
     * @return 命中结果的 集合信息
     */
    @GetMapping("/list/{index}")
    Object queryForList(@PathVariable(name = "index") String index) throws IOException;


}
