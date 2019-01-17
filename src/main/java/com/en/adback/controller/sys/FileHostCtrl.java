package com.en.adback.controller.sys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.sys.FileHost;
import com.en.adback.entity.sys.FileHostLogsEntity;
import com.en.adback.service.sys.FileHostService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/5.
 */
@Api(value="菜单管理",tags={"菜单管理webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/fileHost", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class FileHostCtrl {

    @Autowired
    private FileHostService svr;
    @Autowired
    private UserLogs logs;

    @ApiOperation( value = "获取文件服务器列表",notes = "" +
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
    @GetMapping(value = "/getFileHostList")
    public MessageModel getFileHostList(String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<>();
        List<FileHost> list = svr.getFileHostList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode( list.size()>0 ? "1" :"2");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        logs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询文件服务器");
        return m;
    }

    @ApiOperation( value = "获取菜单列表",notes = "" +
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
    @PostMapping(value = "/insertFileHost")
    public MessageModel insertFileHost(@RequestBody FileHostLogsEntity fileHostArray,HttpServletRequest request){
        MessageModel m= new MessageModel();
        int i=0;
        for(FileHost f :fileHostArray.getFileHostArray()){
            i = svr.insertFileHost(f);
        }
        m.setData(i);
        m.setResultCode( i>0 ? "1" :"2");
        m.setResultMsg("ok");
        String loginUserId = fileHostArray.getLoginUserId();
        String loginGroupRoleId = fileHostArray.getLoginGroupRoleId();
        String loginRoleId = fileHostArray.getLoginRoleId();
        String ip= Common.getIpAddr(request);
        logs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"更新文件服务器");
        return m;
    }

}
