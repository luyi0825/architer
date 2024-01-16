package io.github.architers.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.UUID;

/**
 * websocket启动类
 *
 * @author luyi
 */
@SpringBootApplication
public class WebsocketStarter {
    public static void main(String[] args) {

        new SpringApplication(WebsocketStarter.class).run(args);



    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000L);
                        for (WebSocketServer value : WebSocketServer.getSessionmap().values()) {
                            String message = UUID.randomUUID().toString();
                            System.out.println("send message:" + message);
                            value.sendMessage(message);
                        }
                    } catch (Exception e) {

                    }
                }


            }
        }).start();
        return new ServerEndpointExporter();
    }
}
