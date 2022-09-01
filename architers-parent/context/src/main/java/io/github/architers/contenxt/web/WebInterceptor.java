package io.github.architers.contenxt.web;


import io.github.architers.context.cache.model.InvalidCache;
import io.github.architers.context.cache.operation.BaseCacheOperation;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.Nullable;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;



/**
 * @author luyi
 * 缓存拦截器
 */
public class WebInterceptor implements WebRequestInterceptor {




    @Override
    public void preHandle(WebRequest request) throws Exception {

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
