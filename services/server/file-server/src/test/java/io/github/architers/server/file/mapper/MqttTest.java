package io.github.architers.server.file.mapper;

import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.MqttSubscription;

import java.nio.charset.StandardCharsets;

public class MqttTest {
    public static void main(String[] args) {
        String subTopic = "testtopic/#";
        String pubTopic = "testtopic/1";
        String content = "Hello World";
        int qos = 2;
        String broker = "tcp://broker.emqx:1883";
        String clientId = "emqx_test1";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);

            // MQTT connection option
            MqttConnectionOptions connOpts = new MqttConnectionOptions();
            connOpts.setUserName("emqx_test");
            connOpts.setPassword("emqx_test_password".getBytes(StandardCharsets.UTF_8));
            // retain session
           // connOpts.setCleanSession(true);

            // set callback
            client.setCallback(new OnMessageCallback());

            // establish a connection
            System.out.println("Connecting to broker: " + broker);
            client.connect(connOpts);

            System.out.println("Connected");
            System.out.println("Publishing message: " + content);
            MqttSubscription mqttSubscription = new  MqttSubscription(subTopic);
            // Subscribe
            client.subscribe(new MqttSubscription[]{mqttSubscription});

            // Required parameters for message publishing
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(pubTopic, message);
            System.out.println("Message published");

           // client.disconnect();
           // System.out.println("Disconnected");
           // client.close();
           // System.exit(0);
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
