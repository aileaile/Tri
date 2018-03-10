package com.LL.Triangle.webSocket;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.webSocket.configure.GetHttpSessionConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint(value="/lobbyWS",configurator=GetHttpSessionConfigurator.class)
public class LobbyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    public static Map<String,Session> sessionMap = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String httpSessionId;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config){
        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSessionId=httpSession.getId();
        sessionMap.put(httpSessionId,session);     //加入map中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        broadcastMsg("一个用户加入了服务器，当前在线人数为"+ getOnlineCount()+"。");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        sessionMap.remove(httpSessionId);  //从set中删除
        //TableOne.leaveSeat(new User(httpSessionId));
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        broadcastMsg("一个用户离开了服务器，当前在线人数为"+ getOnlineCount()+"。");

    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        String[] msgs = message.split("\\|");
        if(msgs[0].trim().equals("sys")){
            broadcastMsg(msgs[2]+"准备了");
        }else{
            broadcastMsg(message);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息给指定httpSessionID的客户端
     */
    public static void sendMessage(String desHttpSessionId,String message) {
        try {
            //阻塞式的消息发送方式
            sessionMap.get(desHttpSessionId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //非阻塞式的消息传输方式
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 回复消息给当前客户端
     */
    public void reply(String message) {
        try {
            //阻塞式的消息发送方式
            this.sessionMap.get(httpSessionId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //非阻塞式的消息传输方式
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 广播消息
     */
    public static void broadcastMsg(String message){
        for(String httpSessionId: sessionMap.keySet()){
            try {
                String msgJson = "{\"msgType\":\"broadcast\",\"msg\":\""+message+"\"}";
                sessionMap.get(httpSessionId).getBasicRemote().sendText(msgJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 广播消息
     */
    public static void sendAll(String message){
        for(String httpSessionId: sessionMap.keySet()){
            try {
                sessionMap.get(httpSessionId).getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        LobbyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        LobbyWebSocket.onlineCount--;
    }
}