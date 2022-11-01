package io.github.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(Integer.MIN_VALUE)
public class MyFilter implements Filter {

    private static Map<String, String> urlMap = new HashMap<>();

    static {
        urlMap.put("/api/test/1", "/test/test1");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
       String uri = request.getRequestURI();
        String newUri = urlMap.get(uri);

//        if (newUri != null) {
//            URLReWriteRequest httpServletRequest = new URLReWriteRequest(request, newUri);
//            filterChain.doFilter(httpServletRequest, servletResponse);
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }

        if (uri.startsWith("/api")) {
            URLReWriteRequest httpServletRequest = new URLReWriteRequest(request, newUri);
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
