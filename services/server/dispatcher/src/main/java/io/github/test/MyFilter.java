package io.github.test;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
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
        String newUri = urlMap.get(request.getRequestURI());

        if (newUri != null) {
            URLReWriteRequest httpServletRequest = new URLReWriteRequest(request, newUri);
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        if (request.getRequestURI().startsWith("/api")) {
            URLReWriteRequest httpServletRequest = new URLReWriteRequest(request, newUri);
            filterChain.doFilter(httpServletRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }
}
