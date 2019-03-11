package com.en.adback.controller.sys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.service.sys.IGroupRoleService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/17.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api/grouprole", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class GroupRoleCtrl {

    @Autowired
    private IGroupRoleService svr;
    @Autowired
    private GroupRoleLogs logs;
    @Autowired
    private UserLogs ulogs;


    @ApiOperation( value = "获取角色分组列表",notes = "" +
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
    @GetMapping(value = "/getGroupRoleList")
    public MessageModel getGroupRoleList(int pageSize,int pageNo,String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("pageBegin",(pageNo-1)*pageSize);
        re.put("pageSize",pageSize);
        List<GroupRole> list = svr.getGroupRoleList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询角色分组列表");
        return m;
    }


    @GetMapping(value = "/getGroupRoleListTotal")
    public MessageModel getGroupRoleListTotal(){
        MessageModel m = new MessageModel();
        int total = svr.getGroupRoleListTotal();
        m.setData(total);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }

    @ApiOperation( value = "新增、修改角色分组列表",notes = "" +
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
    @PostMapping(value = "/upsertGroupRole")
    public MessageModel upsertGroupRole(@RequestBody GroupRole groupRole, HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.upsertGroupRole(groupRole);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        if("insert".equals(groupRole.getState())){
            logs.postGroupRoleLogs(groupRole,ip,"新增角色分组");
        }else{
            logs.postGroupRoleLogs(groupRole,ip,"修改角色分组");
        }
        return m;
    }

    @ApiOperation( value = "根据角色分组Id查询",notes = "" +
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
    @GetMapping(value = "/getGroupRoleById")
    public MessageModel getGroupRoleById(String groupRoleId,String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("groupRoleId",groupRoleId);
        List<GroupRole> list = svr.getGroupRoleById(re);
        m.setData(list.size());
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"根据角色分组编号查询");
        return  m;
    }




    @ApiOperation( value = "删除角色分组列表",notes = "" +
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
    @GetMapping(value = "/deleteGroupRole")
    public MessageModel deleteGroupRole(String groupRoleId,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String> re = new HashMap<>();
        re.put("groupRoleId",groupRoleId);
        int i = svr.deleteGroupRole(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除角色分组");
        return m;
    }

    @GetMapping(value = "/checkAppGroupRole")
    public MessageModel checkAppGroupRole(String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        List<GroupRole> list = svr.checkAppGroupRole();
        m.setData(list.size()>0?1:0);
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"检测公众号权限分组是否已存在");
        return m;
    }
}
