package io.github.architers.server.file;

import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws MqttException {
        String subTopic = "testtopic/#";
        String pubTopic = "testtopic/1";
        String content = "Hello World";
        int qos = 2;
        String broker = "tcp://120.48.170.201:1883";
        String clientId = "file_task";
        MqttClient mt = new MqttClient(broker, clientId);
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);

            // MQTT 连接选项
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName("test");
            connOpts.setPassword("test".getBytes());
            // 保留会话
            connOpts.setCleanStart(false);

            // 设置回调
            client.setCallback(new OnMessageCallback());

            // 建立连接
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);

            System.out.println("Publishing message: " + content);

            MqttSubscription mqttSubscription = new MqttSubscription(subTopic);


            // 订阅
            client.subscribe(new MqttSubscription[]{mqttSubscription});

            // 消息发布所需参数
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(pubTopic, message);
            // System.out.println("Message published");

//            client.disconnect();
//            System.out.println("Disconnected");
//            client.close();
//            System.exit(0);
        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }


}
