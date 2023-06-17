package io.github.architers.cache.hook;

import com.alibaba.fastjson.JSON;
import io.github.architers.context.cache.model.BaseCacheParam;
import io.github.architers.context.cache.operate.CacheOperateContext;
import io.github.architers.context.cache.operate.hook.CacheOperateInvocationHook;
import io.github.architers.context.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogCacheOperateInvocationHook implements CacheOperateInvocationHook {
    @Override
    public boolean before(BaseCacheParam cacheOperationParam, CacheOperateContext cacheOperateContext) {
      //  log.info("执行:{}-参数:{}", JSON.toJSONString(cacheOperateContext), JsonUtils.toJsonString(cacheOperationParam));
        return true;
    }

    @Override
    public void after(BaseCacheParam cacheOperationParam, CacheOperateContext cacheOperateContext) {
       // log.info("执行:{}-参数:{}", JSON.toJSONString(cacheOperateContext), JsonUtils.toJsonString(cacheOperationParam));
    }
}
