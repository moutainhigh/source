package com.dchip.cloudparking.web.utils;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Description: websocket简单入门。此类可以向个人发送消息。但是不适合向，因为这里无法适合业务。可以自行改造Map来控制
 * @Author: Gentle
 * @date 2018/9/5  18:43
 */

@Component
@ServerEndpoint(value = "/ws/myWebSocket/{parkingId}")
public class MyWebSocket {
    //每个客户端都会有相应的session,服务端可以发送相关消息

    private Session session;
    private String parkingId;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //J.U.C包下线程安全的类，主要用来存放每个客户端对应的webSocket连接，为什么说他线程安全。在文末做简单介绍
    private static ConcurrentHashMap<String, MyWebSocket> webSocketSet = new ConcurrentHashMap<>();

    //private static CopyOnWriteArraySet<MyWebSocket> copyOnWriteArraySet = new CopyOnWriteArraySet<MyWebSocket>();
    private static Log log = LogFactory.getLog(MyWebSocket.class);


    /**
     * 打开连接。进入页面后会自动发请求到此进行连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "parkingId") String parkingId) {
        this.session = session;
        this.parkingId = parkingId;  //接受发送消息的人编号
        webSocketSet.put(parkingId, this); //加入set中
        addOnlineCount();           //在线数加1
        log.info("用户" + parkingId + "加入！当前在线人数为" + getOnlineCount());
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
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给X用户
        String sendMessage = message.split("[|]")[0];
        String sendUserId = message.split("[|]")[1];
        try {
            if (sendUserId.equals("0")) {
                sendtoAll(sendMessage);
            } else {
                sendtoUser(sendMessage, sendUserId);
            }
        } catch (IOException e) {
            e.printStackTrace();
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


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     *
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendtoUser(String message, String sendUserId) throws IOException {
        if (webSocketSet.get(sendUserId) != null) {
            if (!parkingId.equals(sendUserId)) {
                webSocketSet.get(sendUserId).sendMessage("用户" + parkingId + "发来消息：" + " <br/> " + message);
            } else {
                webSocketSet.get(sendUserId).sendMessage(message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线", parkingId);
        }
    }
    
    /**
     * 遍历所有用户，找到该停车场下的保安操作
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendtoAppointUser(String message,String sendUserId) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
            	if(key.indexOf(sendUserId) != -1) {
            		webSocketSet.get(key).sendMessage(message);
            	}
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
    }


    /**
     * springboot内置tomcat的话，需要配一下这个。。如果没有这个对象，无法连接到websocket
     * 别问为什么。。很坑。。。
     *
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        //这个对象说一下，貌似只有服务器是tomcat的时候才需要配置,具体我没有研究
        return new ServerEndpointExporter();
    }

    /**
     * 发送信息给所有人
     *
     * @param message
     * @throws IOException
     */
    public void sendtoAll(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public Session getSession() {
        return session;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }

    public String getParkingId() {
        return parkingId;
    }


}
