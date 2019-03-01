package com.en.adback.controller.analisys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.PlayLlogExcelCtrl;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.PlayLog;
import com.en.adback.service.analisys.PlayLogService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="播放日志统计",tags={"播放日志统计webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/playlog", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class PlayLogCtrl {

    @Autowired
    private PlayLogService svr;
    @Autowired private UserLogs ulogs;

    @ApiOperation( value = "播放日志统计查询",notes = "播放日志统计查询" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginPlayTime", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endPlayTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertId", value = "广告ID", required = true, dataType = "String", paramType = "query"),
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getPlayLlogList")
    public MessageModel getPlayLlogList(String beginDate,String endDate, String beginPlayTime, String endPlayTime, String advertId,String deviceId,
                                        String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request) throws ParseException {
        MessageModel model=new MessageModel();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("beginDate",beginDate);
        map.put("endDate",endDate);
        map.put("beginPlayTime",beginPlayTime);
        map.put("endPlayTime",endPlayTime);
        map.put("advertId",advertId);
        map.put("deviceId",deviceId);
        List<PlayLog> list = svr.getPlayLlogList(map);
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"播放日志统计查询");

        return model;
    }



    @ApiOperation( value = "查询数据生成表格",notes = "" +
            " 返回字段：list{}" +
            " resultCode," +
            " resultMsg : 'ok' ：成功 ，否则返回错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginDate", value = "开始日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束日期", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "beginPlayTime", value = "开始时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endPlayTime", value = "结束时间", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "advertId", value = "广告ID", required = true, dataType = "String", paramType = "query"),
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/getLogExcel")
    public MessageModel logsExcel(@RequestBody Map<String,Object> map1,HttpServletRequest request) throws ParseException {
        MessageModel m = new MessageModel();
        Map<String,Object> map = new HashMap<>();
        map.put("beginDate", map1.get("beginDate"));
        map.put("endDate", map1.get("endDate"));
        map.put("beginPlayTime", map1.get("beginPlayTime"));
        map.put("endPlayTime", map1.get("endPlayTime"));
        map.put("advertId", map1.get("advertId"));
        map.put("advertName", map1.get("advertName"));
        map.put("putInKind", map1.get("putInKind"));
        map.put("deviceIds", map1.get("deviceIds"));
        map.put("playAlone", map1.get("playAlone"));
        map.put("deviceId", map1.get("deviceId"));
        List<PlayLog> list = svr.getPlayLlogList(map);
        //将数据写入表格中
        PlayLlogExcelCtrl mec=new PlayLlogExcelCtrl();
        try {
            mec.writeLogExcel(list,map);
            map.clear();
            map.put("url","static/excels/playlog.xls");
            m.setData(map);
            m.setResultCode("1");
            m.setResultMsg("success");
        } catch (IOException e) {
            m.setResultCode("2");
            m.setResultMsg("error");
        }
        String ip= Common.getIpAddr(request);
       ulogs.insertGetLogs(map1.get("loginUserId").toString(),map1.get("loginGroupRoleId").toString(),map1.get("loginRoleId").toString(),ip,"播放日志导出Excel");
        return m;
    }
}
