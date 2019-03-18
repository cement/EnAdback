package com.en.adback.websocket;


import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.yeauty.pojo.Session;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class WsSessionManager {

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap();


    public static Session putSession(String deviceId,Session session){
        return sessionMap.put(deviceId, session);
    }
    public static Session getSession(String deviceId){
        return sessionMap.get(deviceId);
    }

    public static Session removeSession(String deviceId){
        return sessionMap.remove(deviceId);
    }


    public static void sendActionByDeviceId(String deviceId,String action){
         Session session = sessionMap.get(deviceId);
        if (!Objects.isNull(session)) {

            session.sendText(action);
            log.info("=== 發送命令：{}",action);
        }else{
            log.info("客户端[{}]未在线！",deviceId);
        }

    }

    public static void sendActionByDeviceIds(List<String> deviceIds, String action){
        deviceIds.forEach(id->sendActionByDeviceId(id,action));
    }


    public static void broadcastAction(String action){
        sessionMap.keySet().forEach(id->sendActionByDeviceId(id,action));
    }

    public static void sendPingFrame(Session session){
        session.channel().writeAndFlush(new PingWebSocketFrame());
    }
    public static void sendPongFrame(Session session){
        session.channel().writeAndFlush(new PongWebSocketFrame());
    }
}
