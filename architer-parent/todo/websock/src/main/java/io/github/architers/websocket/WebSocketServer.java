package io.github.architers.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket服务端
 *
 * @author 司马缸砸缸了
 * @date 2019/7/29 13:46
 */
@ServerEndpoint("/websocket/{userId}")
@Component
@Slf4j
public class WebSocketServer {


    private static RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public RedisTemplate<Object, Object> setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        return WebSocketServer.redisTemplate = redisTemplate;
    }

    public WebSocketServer() {
        System.out.println("new ");
    }

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    // concurrent包的线程安全Map，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 接收sid
    private String userId = "";

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(MyWsSession session, @PathParam("userId") String userId) {
        this.session = session;
        System.out.println(this);
        websocketMap.put(userId, this);
        redisTemplate.opsForHash().put("test", userId, session);
        log.info("websocketMap->" + JSON.toJSONString(websocketMap));
        // webSocketSet.add(this); //加入set中
        addOnlineCount(); // 在线数加1
        log.info("有新窗口开始监听:" + userId + ",当前在线连接数为" + getOnlineCount());
        this.userId = userId;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (websocketMap.get(this.userId) != null) {
            websocketMap.remove(this.userId);
            // webSocketSet.remove(this); //从set中删除
            subOnlineCount(); // 在线数减1
            log.info("有一连接关闭！当前在线连接数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自窗口" + userId + "的信息:" + message);
        if (StringUtils.hasText(message)) {
            JSONArray list = JSONArray.parseArray(message);
            for (int i = 0; i < list.size(); i++) {
                try {
                    // 解析发送的报文
                    JSONObject object = list.getJSONObject(i);
                    String toUserId = object.getString("toUserId");
                    String contentText = object.getString("contentText");
                    object.put("fromUserId", this.userId);
                    // 传送给对应用户的websocket
                    if (StringUtils.hasText(toUserId) && StringUtils.hasText(contentText)) {
                        WebSocketServer socketx = websocketMap.get(toUserId);
                        // 需要进行转换，userId
                        if (socketx != null) {
                            socketx.sendMessage(JSON.toJSONString(object));
                            // 此处可以放置相关业务代码，例如存储到数据库
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 单发自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("推送消息到窗口" + userId + "，推送内容:" + message);
        // 可以通过SpringContextUtils得到bean,进行数据库操作
        WebSocketServer webSocketServer = websocketMap.get(userId);
        if (webSocketServer != null) {
            webSocketServer.sendMessage(message);
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfoAll(String message) throws IOException {
        log.info("推送消息到所有窗口，推送内容:" + message);

        for (Map.Entry<String, WebSocketServer> entry : websocketMap.entrySet()) {
            WebSocketServer item = entry.getValue();
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static ConcurrentHashMap<String, WebSocketServer> getSessionmap() {
        return WebSocketServer.websocketMap;
    }

}

