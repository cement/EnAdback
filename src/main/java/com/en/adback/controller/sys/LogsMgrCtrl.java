package com.en.adback.controller.sys;

import com.en.adback.common.MessageModel;
import com.en.adback.controller.MakeExcelCtrl;
import com.en.adback.entity.Logs;
import com.en.adback.service.sys.LogsService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(value="日志查询",tags={"日志查询webapi 接口"})
@RestController
@RequestMapping(value = "/api/logsMgr", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class LogsMgrCtrl {

    @Autowired private LogsService svr;

    @ApiOperation( value = "查询日志信息",notes = "" +
            " 返回字段：list{" +
            " userId(用户账号)" +
            " groupRoleId（用户分组id）" +
            " roleId（用户角色id）" +
            " ip（操作ip）" +
            " logContent（日志内容）" +
            " actionTime（操作时间）}" +
            " resultCode," +
            " resultMsg : 'ok' ：成功 ，否则返回错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupRoleId", value = "所属分组id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户账号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "分页查询 当前页数", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页查询每页数量", required = false, dataType = "int", paramType = "query"),
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/logsList")
    public MessageModel logsList(String userId,String groupRoleId,String roleId,String beginTime,String endTime,int pageNo,int pageSize){
        MessageModel m = new MessageModel();
        Map<String,Object> map = new HashMap<>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        map.put("userId",userId);
        map.put("groupRoleId",groupRoleId);
        map.put("roleId",roleId);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("pageBegin",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Logs> list=svr.getLogsList(map);
        map.clear();
        map.put("list",list);
        m.setData(map);
        m.setResultCode("1");
        m.setResultMsg("success");
        return m;
    }

    @ApiOperation( value = "查询日志信息",notes = "" +
            " 返回字段：list{" +
            " }" +
            " resultCode," +
            " resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            " data:total(总数)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupRoleId", value = "所属分组id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户账号", required = false, dataType = "String", paramType = "query"),
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/logsListTotal")
    public MessageModel logsListTotal(String userId,String groupRoleId,String roleId,String beginTime,String endTime){
        MessageModel m = new MessageModel();
        Map<String,Object> map = new HashMap<>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        map.put("userId",userId);
        map.put("groupRoleId",groupRoleId);
        map.put("roleId",roleId);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        String total=svr.getLogsListTotal(map);
        map.clear();
        map.put("total",total);
        m.setData(map);
        m.setResultCode("1");
        m.setResultMsg("success");
        return m;
    }


    @ApiOperation( value = "查询数据生成表格",notes = "" +
            " 返回字段：list{}" +
            " resultCode," +
            " resultMsg : 'ok' ：成功 ，否则返回错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beginTime", value = "查询开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "查询结束时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "groupRoleId", value = "所属分组id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户账号", required = false, dataType = "String", paramType = "query"),
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/logsExcel")
    public MessageModel logsExcel(String userId,String groupRoleId,String roleId,String beginTime,String endTime){
        MessageModel m = new MessageModel();
        Map<String,Object> map = new HashMap<>();
        //把截止日期加长一天
        if (endTime !=null && !endTime.equals("")) {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = null;
            try {
                d = new Date(f.parse(endTime).getTime() + 24 * 3600 * 1000);
                endTime = f.format(d);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        map.put("userId",userId);
        map.put("groupRoleId",groupRoleId);
        map.put("roleId",roleId);
        map.put("beginTime",beginTime);
        map.put("endTime",endTime);
        map.put("pageBegin",0);
        map.put("pageSize",1000);
        List<Logs> list=svr.getLogsList(map);
        //将数据写入表格中
        MakeExcelCtrl mec=new MakeExcelCtrl();
        try {
            map.clear();
            mec.writeLogExcel(list);
            map.put("url","static/excels/日志信息.xls");
            m.setData(map);
            m.setResultCode("1");
            m.setResultMsg("success");
        } catch (IOException e) {
            m.setResultCode("2");
            m.setResultMsg("error");
        }
        return m;
    }
}
