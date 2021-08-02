package com.business.base.codemap.api;


import com.business.base.codemap.entity.CodeMapItem;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author luyi
 * 代码项API接口层
 */
@RestController
@RequestMapping("/codeConvertItemApi")
public interface CodeMapItemApi {

    /**
     * 通过convertCode查询代码集项
     *
     * @return convertCode对应的代码集项
     */
    @GetMapping("/findByConvertCode/{convertCode}")
    List<CodeMapItem> findByConvertCode(@PathVariable(name = "convertCode") String convertCode);


}
