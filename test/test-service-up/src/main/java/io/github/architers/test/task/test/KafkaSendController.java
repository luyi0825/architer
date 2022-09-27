package io.github.architers.test.task.test;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/kafkaSend")
public class KafkaSendController {

    @Resource
    KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/send")
    public void send() {
        kafkaTemplate.send("testTopic", "34");
    }

}
