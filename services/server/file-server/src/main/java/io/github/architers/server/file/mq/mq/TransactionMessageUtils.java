package io.github.architers.server.file.mq.mq;

import org.springframework.messaging.Message;

/**
 * 事务消息工具类
 *
 * @author luyi
 */
public final class TransactionMessageUtils {


    /**
     * 增加业务类型
     */
    public static void addBusinessKey(final Message<?> mgn, String type) {
        mgn.getHeaders().put(LocalTransactionBusinessKey.BUSINESS_KEY_HEADER, type);
    }

    /**
     * 得到业务类型
     */
    public static String getBusinessKey(final Message<?> mgn) {
        return mgn.getHeaders().get(LocalTransactionBusinessKey.BUSINESS_KEY_HEADER, String.class);
    }

}
