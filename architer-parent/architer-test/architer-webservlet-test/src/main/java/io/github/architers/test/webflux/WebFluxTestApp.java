package io.github.architers.test.webflux;

import io.github.architers.context.web.WebFluxMvcAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebFluxMvcAutoConfiguration.class)
public class WebFluxTestApp {

    public static void main(String[] args) {
        new SpringApplication(WebFluxTestApp.class).run(args);
    }

}
