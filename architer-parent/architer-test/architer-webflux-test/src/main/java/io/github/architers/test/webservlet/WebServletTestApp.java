package io.github.architers.test.webservlet;

import io.github.architers.context.web.WebServletMvcAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebServletMvcAutoConfiguration.class)
public class WebServletTestApp {

    public static void main(String[] args) {
        new SpringApplication(WebServletTestApp.class).run(args);
    }

}
