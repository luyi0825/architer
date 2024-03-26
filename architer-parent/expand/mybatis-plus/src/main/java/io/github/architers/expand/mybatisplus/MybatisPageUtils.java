package io.github.architers.expand.mybatisplus;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.github.architers.common.model.page.IPagination;
import io.github.architers.common.model.response.PageResponse;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 分页工具类
 *
 * @author luyi
 * @since 1.0.3
 */
public final class MybatisPageUtils {
    private final static int TWO_PAGE_NUM = 2;
    private final static int ONE_PAGE_NUM = 1;

    /**
     * 将PageHelper分页后的list转为分页信息
     */
    public static <T> PageResponse<T> restPage(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        PageResponse<T> pageResult = new PageResponse<>();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalPage(pageInfo.getPages());
        pageResult.setList(pageInfo.getList());
        pageResult.setTotalCount(pageInfo.getTotal());
        return pageResult;
    }

    /**
     * mybatisPlus分页查询
     *
     * @param supplier 数据查询
     */

    public static <T> PageResponse<T> pageQuery(IPagination pageParam, Supplier<List<T>> supplier) {
        try (Page<T> page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize())) {
            List<T> list = supplier.get();
            return MybatisPageUtils.restPage(list);
        }
    }

    /**
     * mybatisPlus分页查询
     *
     * @param dataQueryFunction 数据查询
     */

    public static <T> void pageQueryAll(IPagination pageParam, Function<IPagination, Page<T>> dataQueryFunction) {
        Page<T> page = null;
        try {
            page = PageHelper.startPage(pageParam.getPageNum(), pageParam.getPageSize());
            //第一次查询
            page = dataQueryFunction.apply(pageParam);
            int pages = page.getPages();
            //从第二页开始分页查询
            if (pages > 1) {
                for (int i = TWO_PAGE_NUM; i <= pages; i++) {
                    //后边就不count了
                    page = PageHelper.startPage(i, pageParam.getPageSize(),
                            false);
                    page = dataQueryFunction.apply(pageParam);
                }
            }
        } finally {
            if (page != null) {
                page.close();
            }
        }
    }

}
