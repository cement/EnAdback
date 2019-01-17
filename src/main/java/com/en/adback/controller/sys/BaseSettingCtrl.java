package com.en.adback.controller.sys;


import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.sys.SysSetting;
import com.en.adback.service.sys.SysSettingService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value="系统设置",tags={"系统基本设置webapi 接口"})
@RestController
@RequestMapping(value = "/api/sysSetting", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class BaseSettingCtrl {

    @Autowired private SysSettingService svr;
    @Autowired private UserLogs ulogs;
    @Autowired private BaseSettingLogs logs;

    @ApiOperation( value = "系统设置查询",notes = "系统设置查询" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/getSettingParams")
    public MessageModel getSettingParams(String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel model=new MessageModel();
        SysSetting setting=svr.getSettingParams();
        model.setData(setting);
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询系统设置");
        return model;
    }

    @ApiOperation( value = "系统参数设置",notes = "系统参数设置" )
    @ApiImplicitParams({
    })
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/settingParams")
    public MessageModel settingParams(@RequestBody SysSetting sysSetting,HttpServletRequest request){
        MessageModel model=new MessageModel();
        int flag=svr.settingParams(sysSetting);
        model.setResultCode(flag>0?"1":"2");
        model.setResultMsg("success");
        String ip = Common.getIpAddr(request);
        logs.postSettingParamsLogs(sysSetting,ip,"更新系统参数");
        return model;
    }
}
