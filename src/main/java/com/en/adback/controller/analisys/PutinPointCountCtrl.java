package com.en.adback.controller.analisys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.service.analisys.PutinPointCountSerivce;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="投放点位统计",tags={"投放点位统计webapi接口"})
@RestController
@RequestMapping(value = "/api/putinpointcount", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class PutinPointCountCtrl {

    @Autowired
    private PutinPointCountSerivce svr;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "投放点位统计查询",notes = "投放点位统计查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/point")
    public MessageModel advertList(String year,String loginUserId,
                                   String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("year",year);
        List<Map<String,Object>> list = svr.getPlacementPointStatistics(map);
        List<Map<String,Object>> broadcastFrequency = svr.getBroadcastFrequency(map);
        map.put("list",list);
        map.put("broadcastFrequency",broadcastFrequency);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"投放点位统计查询");
        return model;
    }



    @ApiOperation( value = "点位播放频次排名",notes = "点位播放频次排名" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getPointStatistics")
    public MessageModel getPointStatistics(String year,String loginUserId,
                                           String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("year",year);
        List<Map<String,Object>> list = svr.getPointStatistics(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"点位播放频次排名");
        return model;
    }



    @ApiOperation( value = "点位投放广告数量统计",notes = "点位投放广告数量统计" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getAdvertisingStatistics")
    public MessageModel getAdvertisingStatistics(String year,String loginUserId,
                                                 String loginGroupRoleId,String loginRoleId,HttpServletRequest request)
    {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("year",year);
        List<Map<String,Object>> OrientationOnTheArray = svr.getOrientationOnTheArray(map);// 获取 广告投放总数
        List<Map<String,Object>> TheTailArray = svr.getTheTailArray(map); // 获取 开屏广告 统计
        map.put("OrientationOnTheArray",OrientationOnTheArray);
        map.put("TheTailArray",TheTailArray);
        model.setData(map);
        model.setResultCode("1");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"点位投放广告数量统计");
        return model;
    }

















}
