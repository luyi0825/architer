package io.github.architers.context.web;

import io.github.architers.context.web.webservlet.ResponseResultHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author luyi
 * webmvc配置
 * <li>重写configureMessageConverters，ControllerAdvice处理后，数据返回的都是返回的固定的对象，
 * 会导致StringHttpMessageConverter转换异常
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerComposite#configureMessageConverters(List)
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#getMessageConverters()
 * 这里去掉StringHttpMessageConverter，对于text/plain类型用ResponseResultHttpMessageConverter替换
 * @see ResponseResultHttpMessageConverter
 * </li>
 */
public class WebmvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);
    }
}
