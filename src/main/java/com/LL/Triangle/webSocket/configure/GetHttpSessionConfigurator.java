package com.LL.Triangle.webSocket.configure;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * 通过WebSocketSession获取HttpSession的配置类
 * @Author ll
 */
public class GetHttpSessionConfigurator extends Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec,HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }

}