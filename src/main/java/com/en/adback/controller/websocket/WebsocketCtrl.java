package com.en.adback.controller.websocket;


import com.en.adback.websocket.WsSessionManager;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value="广告替换、下架、下发",tags={"广告替换、下架、下发webapi接口"})
@RestController
@RequestMapping(value = "/api/websocket", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class WebsocketCtrl {




    @RequestMapping(value = "/dispatch ", method = {RequestMethod.GET, RequestMethod.POST})
    public void sendAction(List<String> devideId, String action){
        devideId.forEach(id->WsSessionManager.sendActionByDeviceId(id,action));

    }
    @RequestMapping(value = "/broadcast", method = {RequestMethod.GET, RequestMethod.POST})
    public void broadcastAction(String action){
        WsSessionManager.broadcastAction(action);
    }
}


/**
 * code:xxx.
 * action:xxx,
 * data:{
 *     fileName:xxx,
 *     replacedFile:xxx
 * }
 *
 *
 *
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
