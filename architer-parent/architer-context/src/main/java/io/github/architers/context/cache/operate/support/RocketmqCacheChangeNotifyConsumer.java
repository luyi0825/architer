package io.github.architers.context.cache.operate.support;

import io.github.architers.context.cache.enums.CacheDeleteType;
import io.github.architers.context.cache.model.BatchDeleteParam;
import io.github.architers.context.cache.model.DeleteAllParam;
import io.github.architers.context.cache.model.DeleteParam;
import io.github.architers.context.cache.operate.TwoLevelCacheOperate;
import io.github.architers.context.utils.JsonUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 * rocketmq延迟删除通知
 *
 * @author luyi
 */
@RocketMQMessageListener(topic = "xxx", consumerGroup = "xxx_consumer", selectorExpression = "${spring.application.name}", messageModel = MessageModel.BROADCASTING)
public class RocketmqCacheChangeNotifyConsumer implements RocketMQListener<MessageExt> {

    private TwoLevelCacheOperate threeLevelCacheOperate;

    @Override
    public void onMessage(MessageExt message) {
        CacheDeleteType cacheDeleteType = CacheDeleteType.valueOf(message.getProperty("delete_type"));
        switch (cacheDeleteType) {
            case single:
                DeleteParam deleteParam = JsonUtils.readValue(message.getBody(), DeleteParam.class);
                threeLevelCacheOperate.getLocalCacheOperate().delete(deleteParam);
                break;
            case batch:
                BatchDeleteParam deleteBatchParam = JsonUtils.readValue(message.getBody(), BatchDeleteParam.class);
                threeLevelCacheOperate.getLocalCacheOperate().batchDelete(deleteBatchParam);
                break;
            case all:
                DeleteAllParam deleteAllParam = JsonUtils.readValue(message.getBody(), DeleteAllParam.class);
                threeLevelCacheOperate.getLocalCacheOperate().deleteAll(deleteAllParam);

        }
    }
}
