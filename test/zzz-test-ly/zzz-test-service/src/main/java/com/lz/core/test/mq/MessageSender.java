package com.lz.core.test.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lz.core.utils.JsonUtils;
import lombok.Data;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("/send/{message}")
    public String sendMessage(@PathVariable String message) {

        Map<String, Object> params = new HashMap<>();
        // params.put("x-dead-letter-exchange", DEAD_FANOUT_EXCHANGE);
        for (int i = 0; i < 1; i++) {
            params.put("try-count", i);
            // params.put("x-dead-letter-routing-key", "dead_queue_gathering_data");
            //  params.put("x-message-ttl", maxInterval);
            User user = new User();
            ObjectMapper OBJECT_MAPPER = new ObjectMapper();
            user.setUsername("username" + i);
            String userJson = JsonUtils.toJsonString(user);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.getHeaders().putAll(params);
            // EventingBasicConsumer consumer = new EventingBasicConsumer(channel);
            rabbitTemplate.send("queue_gathering_data", new Message(userJson.getBytes(StandardCharsets.UTF_8), new MessageProperties()));
        }
        return message;

    }


    @RequestMapping("/send/physicalTable")
    public String sendMessage() {

        Map<String, Object> params = new HashMap<>();
        // params.put("x-dead-letter-exchange", DEAD_FANOUT_EXCHANGE);
        for (int i = 0; i < 1; i++) {
            params.put("try-count", i);
            // params.put("x-dead-letter-routing-key", "dead_queue_gathering_data");
            //  params.put("x-message-ttl", maxInterval);
            User user = new User();
            ObjectMapper OBJECT_MAPPER = new ObjectMapper();
            user.setUsername("username" + i);
            String userJson = JsonUtils.toJsonString(user);
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.getHeaders().put("retryCount", 1);
            // EventingBasicConsumer consumer = new EventingBasicConsumer(channel);
            rabbitTemplate.send("queue_physical_table", new Message(userJson.getBytes(StandardCharsets.UTF_8), messageProperties));
        }
        return "true";

    }


    @RequestMapping("/send/task")
    public String sendTask() throws JsonProcessingException {

        Map<String, Object> params = new HashMap<>();
        // params.put("x-dead-letter-exchange", DEAD_FANOUT_EXCHANGE);

        for (int i = 0; i < 30_000; i++) {
            List<RespondentReportTask> taskList = new ArrayList<>();
            RespondentReportTask respondentReportTask = new RespondentReportTask();
            respondentReportTask.setRespondentId("respondent" + i);
            respondentReportTask.setGatherWay("0");
            respondentReportTask.setRespondentTaskStatus("9");
            respondentReportTask.setAreaId("000");
            respondentReportTask.setPeriodId("20210030");
            respondentReportTask.setReportCode("YYC201_2021");
            taskList.add(respondentReportTask);
            ObjectMapper objectMapper = new ObjectMapper();

            rabbitTemplate.send("queue_respondent_report_task", new Message(objectMapper.writeValueAsString(taskList).getBytes(StandardCharsets.UTF_8), new MessageProperties()));

            // EventingBasicConsumer consumer = new EventingBasicConsumer(channel);
        }

        return "true";

    }

    @Data
    class User implements Serializable {
        private String username;
    }


}
