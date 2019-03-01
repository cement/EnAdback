package com.en.adback.controller;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.devicemgr.DeviceGroup;
import com.en.adback.service.DeviceMgrService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 设备管理
@Api(value="设备管理",tags={"设备分组webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/devicemgr", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class DeviceMgrCtrl {
    @Autowired
    private DeviceMgrService svr;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "查询所有分组",notes = "查询所有分组" )
    @ApiImplicitParams({
              })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deviceGroupList")
    public MessageModel deviceGroupList(String devGroupName,String devGroupIds,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> paras = new HashMap<String, Object>(){{
            put("devGroupName" , devGroupName== null ? "" : devGroupName);
            put("devGroupIds", (devGroupIds== null||devGroupIds.equals("")) ? "" :  "'" + devGroupIds.replace(",","','") +"'"  );
        }};
        List<DeviceGroup> list = svr.deviceGroupList(paras);
        model.setData(new HashMap<String,Object>(){{
            put("list",list);
        }});
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询设备分组");
        return model;
    }

    @ApiOperation( value = "设备分组保存",notes = "设备分组保存" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/saveDeviceGroup")
    public MessageModel saveDeviceGroup(@RequestBody DeviceGroup deviceGroup,HttpServletRequest request){
        MessageModel model=new MessageModel();
        int i1 = svr.saveDeviceGroup(deviceGroup);
        model.setResultCode("1");
        model.setResultMsg("success");
        String loginUserId = deviceGroup.getLoginUserId();
        String loginGroupRoleId = deviceGroup.getLoginGroupRoleId();
        String loginRoleId = deviceGroup.getLoginRoleId();
        String ip=Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"保存设备分组");
        return model;
    }

    @ApiOperation( value = "设备分组删除",notes = "设备分组删除" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/delDeviceGroup")
    public MessageModel delDeviceGroup(String devGroupId,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        Map<String,Object> paras = new HashMap<String, Object>(){{
            put("devGroupId" , devGroupId);
        }};
        int i1 = svr.delDeviceGroup(paras);
        model.setResultCode("1");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除设备分组");
        return model;
    }


    @ApiOperation( value = "查询数据生成表格",notes = "" +
            " 返回字段：list{}" +
            " resultCode," +
            " resultMsg : 'ok' ：成功 ，否则返回错误信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "devGroupName", value = "设备分组名称", required = false, dataType = "String", paramType = "query"),
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deviceGroupExcel")
    public MessageModel logsExcel(String devGroupName,String devGroupIds,String loginUserId,
                                  String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> map = new HashMap<>();
        map.put("devGroupName" , devGroupName== null ? "" : devGroupName);
        map.put("devGroupIds", (devGroupIds== null||devGroupIds.equals("")) ? "" :  "'" + devGroupIds.replace(",","','") +"'"  );
        List<DeviceGroup> list = svr.deviceGroupList(map);
        //将数据写入表格中
        DeviceMgrExcelCtrl mec=new DeviceMgrExcelCtrl();
        try {
            map.clear();
            mec.writeLogExcel(list);
            map.put("url","static/excels/devicegroupexcel.xls");
            m.setData(map);
            m.setResultCode("1");
            m.setResultMsg("success");
        } catch (IOException e) {
            m.setResultCode("2");
            m.setResultMsg("error");
        }
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询设备分组信息并保存到Excel");
        return m;
    }
}
