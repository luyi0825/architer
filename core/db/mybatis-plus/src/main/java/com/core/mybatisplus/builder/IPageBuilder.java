package com.core.mybatisplus.builder;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.core.mybatisplus.QueryParams;


/**
 * @author luyi
 * @date 2020/12/27
 * 构建IPage
 */
public class IPageBuilder {
    public static IPage buildIPage(QueryParams queryParams) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if (queryParams.getPage() != null) {
            curPage = queryParams.getPage();
        }
        if (queryParams.getLimit() != null) {
            limit = queryParams.getLimit();
        }
        return new Page(curPage, limit);
    }
}
