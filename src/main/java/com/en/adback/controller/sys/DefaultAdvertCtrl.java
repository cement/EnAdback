package com.en.adback.controller.sys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.sys.DefaultAdvert;
import com.en.adback.entity.sys.DefaultAdvertLogsEntity;
import com.en.adback.service.sys.IDefaultAdvertService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/8.
 */
@Api(value="行业管理",tags={"行业管理webapi 接口"})
@RestController
@RequestMapping(value = "/api/defaultAdvert", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class DefaultAdvertCtrl {

    @Autowired
    private IDefaultAdvertService svr;
    @Autowired
    private UserLogs ulogs;

    @ApiOperation( value = "查询默认广告信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @GetMapping(value = "/defaultAdvertList")
    public MessageModel defaultAdvertList(String loginUserId,
                                          String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        List<DefaultAdvert> list = svr.getDefaultAdvertList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("1");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询默认广告");
        return m;
    }


    @ApiOperation( value = "查询默认广告信息",notes = "" +
            " 返回字段：{" +
            "   data : null ,   " +
            "    resultCode," +
            "    resultMsg : 'ok' ：成功 ，否则返回错误信息" +
            "}")
    @ApiImplicitParams({
    }
    )
    @ApiResponses({ @ApiResponse(code = 1, message = "操作成功"),
            @ApiResponse(code = 2, message = "服务器内部异常"),
            @ApiResponse(code =3, message = "权限不足") })
    @PostMapping(value = "/insertDefaultAdvert")
    public MessageModel insertDefaultAdvert(@RequestBody DefaultAdvertLogsEntity defaultAdvertList,HttpServletRequest request){
        MessageModel m = new MessageModel();
         for (DefaultAdvert  dAdvert :  defaultAdvertList.getDefaultAdvertList() )
         {
                svr.insertDefaultAdvert(dAdvert);
         }
        m.setData("1");
        m.setResultCode("1");
        m.setResultMsg("ok");
        String loginUserId = defaultAdvertList.getLoginUserId();
        String loginGroupRoleId = defaultAdvertList.getLoginGroupRoleId();
        String loginRoleId = defaultAdvertList.getLoginRoleId();
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"更新默认广告");
        return  m;
    }

}
