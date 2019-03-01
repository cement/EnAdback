package com.en.adback.websocket;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Objects;

@ServerEndpoint(prefix = "app.ad.websocket")
@Component
public class WebSocketEndpoint {


    public String  deviceId;

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        System.out.println("new connection");
        deviceId = parameterMap.getParameter("vmcNo");
        if (StringUtils.isEmpty(deviceId)){
            session.sendText("bad request!");
        }
        System.out.println(deviceId);
        Session oldSession = WsSessionManager.getSession(deviceId);
        if (Objects.isNull(oldSession)){
           WsSessionManager.putSession(deviceId,session);
        }else{
            oldSession.close();
            WsSessionManager.putSession(deviceId,session);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        WsSessionManager.removeSession(deviceId);
       System.out.println("one connection closed"); 
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        WsSessionManager.removeSession(deviceId);
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello client! your messge is："+message);
    }

    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println(b);
        }
        session.sendBinary(bytes); 
    }

    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    session.close();
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

}