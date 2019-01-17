package com.en.adback.controller;

import com.en.adback.common.MessageModel;
import com.en.adback.entity.devicemgr.DeviceGroup;
import com.en.adback.service.DeviceMgrService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation( value = "查询所有分组",notes = "查询所有分组" )
    @ApiImplicitParams({
              })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/deviceGroupList")
    public MessageModel deviceGroupList(String devGroupName,String devGroupIds){
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
        return model;
    }

    @ApiOperation( value = "设备分组保存",notes = "设备分组保存" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/saveDeviceGroup")
    public MessageModel saveDeviceGroup(@RequestBody DeviceGroup deviceGroup){
        MessageModel model=new MessageModel();
        int i1 = svr.saveDeviceGroup(deviceGroup);
        model.setResultCode("1");
        model.setResultMsg("success");
        return model;
    }

    @ApiOperation( value = "设备分组删除",notes = "设备分组删除" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/delDeviceGroup")
    public MessageModel delDeviceGroup(String devGroupId){
        MessageModel model=new MessageModel();
        Map<String,Object> paras = new HashMap<String, Object>(){{
            put("devGroupId" , devGroupId);
        }};
        int i1 = svr.delDeviceGroup(paras);
        model.setResultCode("1");
        model.setResultMsg("success");
        return model;
    }

}
