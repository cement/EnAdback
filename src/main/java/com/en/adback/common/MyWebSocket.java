package com.en.adback.common;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;

import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;

/**
 * 与设备websocket 通信
 *  a.停播消息{
 *                 action : ‘stopPlay’,
 *         deviceID，// 设备id
 *                 fileName , //文件名称
 * }
 * b.下替换文件消息｛
 *       action: ’changeFile’,
 * deviceID， // 设备id
 *         downLoadFileUrl,//文件下载地址
 *         fileName , //文件名称
 *         replacedFile // 要替换的文件
 * ｝
 *
 * 5.手动下刊 ：接收主程序websocket消息推送：
 *      消息格式：｛
 *         Action:’putDown’,
 * deviceID， // 设备id
 *         fileName , //要下刊文件名称
 * ｝
 */
//@ServerEndpoint(path ="/websocket",port = 8081)
//@Component
public class MyWebSocket {

    @OnOpen
    public void onOpen(Session session, HttpHeaders headers) throws IOException {
        System.out.println("new connection"+session.id());
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("one connection closed");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void OnMessage(Session session, String message) {
        System.out.println(message);
        session.sendText("Hello Netty!");
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
