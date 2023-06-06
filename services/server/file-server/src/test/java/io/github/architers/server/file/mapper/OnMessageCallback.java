package io.github.architers.server.file.mapper;

import org.eclipse.paho.mqttv5.client.IMqttDeliveryToken;
import org.eclipse.paho.mqttv5.client.IMqttToken;
import org.eclipse.paho.mqttv5.client.MqttCallback;
import org.eclipse.paho.mqttv5.client.MqttDisconnectResponse;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;

public class OnMessageCallback implements MqttCallback {
    public void connectionLost(Throwable cause) {
        // After the connection is lost, it usually reconnects here
        System.out.println("disconnect, you can reconnect");
    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
        System.out.println(mqttDisconnectResponse.getReasonString());
    }

    @Override
    public void mqttErrorOccurred(MqttException e) {

    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // The messages obtained after subscribe will be executed here
        System.out.println("Received message topic:" + topic);
        System.out.println("Received message QoS:" + message.getQos());
        System.out.println("Received message content:" + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {

    }

    @Override
    public void connectComplete(boolean b, String s) {

    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {

    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }
}