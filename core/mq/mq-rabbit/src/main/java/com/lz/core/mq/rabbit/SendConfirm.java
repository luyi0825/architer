package com.lz.core.mq.rabbit;


import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Map;


public class SendConfirm implements ApplicationContextAware{
    private RabbitTemplate rabbitTemplate;

    private Map<String,ConfirmCallbackHandler> confirmCallbackHandlerMap;

    public SendConfirm(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @PostConstruct
    public void startInitConfirm(){

        rabbitTemplate.setConfirmCallback((correlationData, ack, s) -> {
           MessageProperties properties= correlationData.getReturnedMessage().getMessageProperties();
          String configHeader=  properties.getHeader(RabbitConstants.CONFIRM_CALLBACK_HEADER_KEY);
            if(StringUtils.isEmpty(configHeader)){
                return;
            }
            ConfirmCallbackHandler confirmCallbackHandler=confirmCallbackHandlerMap.get(configHeader);
            if(confirmCallbackHandler==null){
                throw new IllegalArgumentException(configHeader+"not exist");
            }
            confirmCallbackHandler.confirm(correlationData,ack,s);
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
