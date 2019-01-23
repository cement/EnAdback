package com.en.adback.controller.websocket;


import com.en.adback.websocket.WsSessionManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Api(value="广告替换、下架、下发控制",tags={"广告替换、下架、下发控制webapi接口"})
@RestController
@RequestMapping(value = "/api/websocket", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class WebsocketCtrl {




    @ApiOperation( value = "根据设备编号控制",notes = "根据设备编号控制api接口")
    @RequestMapping(value = "/dispatch ", method = {RequestMethod.GET, RequestMethod.POST})
    public void sendAction(String action,String... devideIds ){
        Arrays.stream(devideIds).forEach(id->WsSessionManager.sendActionByDeviceId(id,action));

    }


    @ApiOperation( value = "全部设备控制",notes = "全部设备控制api接口")
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
