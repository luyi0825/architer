package io.github.architers.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebFluxStarter {
    public static void main(String[] args) {
        new SpringApplication(WebFluxStarter.class).run(args);
    }
}
