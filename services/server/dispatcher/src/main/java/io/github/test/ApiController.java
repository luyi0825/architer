package io.github.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    DispatcherServlet dispatcherServlet;

    private static Map<String, String> urlMap = new HashMap<>();

    static {
        urlMap.put("/api/test/1", "/test/test1");
    }

    @RequestMapping("/**")
    public void test(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newUri = urlMap.get(request.getRequestURI());
        URLReWriteRequest httpServletRequest = new URLReWriteRequest(request,newUri);
        dispatcherServlet.service(httpServletRequest, response);
    }


}
