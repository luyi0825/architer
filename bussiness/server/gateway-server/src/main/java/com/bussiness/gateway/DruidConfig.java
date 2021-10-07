//package com.ly.gateway;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.support.http.StatViewServlet;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class DruidConfig {
//    @ConfigurationProperties(prefix = "spring.datasource")
//    @Bean
//    public DataSource druid(){
//        return  new DruidDataSource();
//    }
//
//    //配置Druid的监控
//    //1、配置一个管理后台的Servlet
//
//    public ServletRegistrationBean statViewServlet(){
//        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
//        Map<String,String> initParams = new HashMap<>();
//
//        initParams.put("loginUsername","admin");
//        initParams.put("loginPassword","123456");
//        //默认就是允许所有访问
//        initParams.put("allow","");
//        initParams.put("deny","192.168.15.21");
//
//        bean.setInitParameters(initParams);
//        return bean;
//    }
//}
