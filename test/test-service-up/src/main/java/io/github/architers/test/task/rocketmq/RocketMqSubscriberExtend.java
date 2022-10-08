package io.github.architers.test.task.rocketmq;

import io.github.architers.context.task.subscriber.SubscriberExtend;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;


public  class RocketMqSubscriberExtend implements SubscriberExtend {

    /**
     * Control how to selector message.
     *
     * @see SelectorType
     */
    SelectorType selectorType() {
        return SelectorType.TAG;
    }

    /**
     * Control which message can be select. Grammar please see {@link SelectorType#TAG} and {@link SelectorType#SQL92}
     */
    String selectorExpression() {
        return "*";
    }


    /**
     * Control consume mode, you can choice receive message concurrently or orderly.
     */
    ConsumeMode consumeMode() {
        return ConsumeMode.CONCURRENTLY;
    }

    /**
     * Control message mode, if you want all subscribers receive message all message, broadcasting is a good choice.
     */
    MessageModel messageModel() {
        return MessageModel.CLUSTERING;
    }


    @Deprecated
    int consumeThreadMax() {
        return 64;
    }


    /**
     * consumer thread number.
     */
    int consumeThreadNumber() {
        return 20;
    }


    /**
     * Max re-consume times.
     * <p>
     * In concurrently mode, -1 means 16;
     * In orderly mode, -1 means Integer.MAX_VALUE.
     */
    int maxReconsumeTimes() {
        return -1;
    }


    /**
     * Maximum amount of time in minutes a message may block the consuming thread.
     */
    long consumeTimeout() {
        return 15L;
    }

    /**
     * Timeout for sending reply messages.
     */
    int replyTimeout() {
        return 3000;
    }


    /**
     * The property of "access-key".
     */
    String accessKey() {
        return RocketMQMessageListener.ACCESS_KEY_PLACEHOLDER;
    }


    /**
     * The property of "secret-key".
     */
    String secretKey() {
        return RocketMQMessageListener.SECRET_KEY_PLACEHOLDER;
    }


    /**
     * Switch flag instance for message trace.
     */
    boolean enableMsgTrace() {
        return false;
    }

    /**
     * The name value of message trace topic.If you don't config,you can use the default trace topic name.
     */
    String customizedTraceTopic() {
        return RocketMQMessageListener.TRACE_TOPIC_PLACEHOLDER;
    }


    /**
     * The property of "name-server".
     */
    String nameServer() {
        return RocketMQMessageListener.NAME_SERVER_PLACEHOLDER;
    }


    /**
     * The property of "access-channel".
     */

    String accessChannel() {
        return RocketMQMessageListener.ACCESS_CHANNEL_PLACEHOLDER;
    }

    /**
     * The property of "tlsEnable" default false.
     */
    String tlsEnable() {
        return "false";
    }


    /**
     * The namespace of consumer.
     */
    String namespace() {
        return "";
    }


    /**
     * Message consume retry strategy in concurrently mode.
     * <p>
     * -1,no retry,put into DLQ directly
     * 0,broker control retry frequency
     * >0,client control retry frequency
     */
    int delayLevelWhenNextConsume() {
        return 0;
    }


    /**
     * The interval of suspending the pull in orderly mode, in milliseconds.
     * <p>
     * The minimum value is 10 and the maximum is 30000.
     */
    int suspendCurrentQueueTimeMillis() {
        return 1000;
    }


    /**
     * Maximum time to await message consuming when shutdown consumer, in milliseconds.
     * The minimum value is 0
     */
    int awaitTerminationMillisWhenShutdown() {
        return 1000;
    }


}
