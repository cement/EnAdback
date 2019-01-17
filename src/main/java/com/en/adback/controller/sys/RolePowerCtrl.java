package com.en.adback.controller.sys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.entity.sys.MenuId;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.RolePower;
import com.en.adback.entity.sys.RolePowerLog;
import com.en.adback.service.IMenuService;
import com.en.adback.service.sys.IRolePowerService;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/12/21.
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/api/rolepower", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class RolePowerCtrl {

    @Autowired
    private IRolePowerService svr;
    @Autowired
    private RolePowerLogs logs;
    @Autowired
    private UserLogs ulogs;
    @Autowired
    private IMenuService menusvr;

    @ApiOperation( value = "获取角色列表",notes = "" +
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
    @GetMapping(value = "/getRoleList")
    public MessageModel getRoleList(String groupRoleId,String loginUserId,
                                    String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re= new HashMap<>();
        re.put("groupRoleId",groupRoleId);
        List<Role> list = svr.getRoleList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询角色列表");
        return m;
    }

    @ApiOperation( value = "获取角色权限列表",notes = "" +
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
    @GetMapping(value = "/getRolePowerList")
    public MessageModel getRolePowerList(String groupRoleId,String roleId,String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        List<RolePower> list = new ArrayList<RolePower>();//存放所有数据的list
        re.put("groupRoleId",groupRoleId);
        re.put("roleId",roleId);
        String menus = svr.getMenus(re);//取出该小组的权限 字符串形式
        if(!"0".equals(menus)){//该小组只拥有部分权限
            String[] menuArray = menus.split(",");// 以逗号隔开 并把每个权限依次放入数组
            for(int i=0;i<menuArray.length;i++){
                String menuId = menuArray[i];//循环取出每个权限编号
                re.put("leafMenuId",menuId);
                String pareMenuIdTrue = menusvr.getPareMenuId(re);//查询对应的上级菜单
                    if("0".equals(pareMenuIdTrue)){
                        re.put("maxMenu",menuId);
                    }else{
                        re.put("maxMenu",pareMenuIdTrue);
                    }
                    List<RolePower> maxMenuList = svr.getMaxMenu(re);
                    list.addAll(maxMenuList);
                re.put("menuId",menuId);
                List<MenuId> listMenuId = svr.getMenuIdList(re);//取出该权限编号的上级编号以及子编号
                if(!"0".equals(listMenuId.get(0).getPareMenuId())){ //如果上级菜单不为零
                    re.put("leafMenuId",menuId); //menuId为最终子页面
                    List<RolePower> leafList = svr.getRolePowerList(re);
                    list.addAll(leafList);//把查询结果放入最终的list 传给前台
                }else{
                    re.put("pareMenuId",menuId);//menuId为二级菜单 如100、800等
                    List<RolePower> pareMenuList = svr.getMaxMenuRolePower(re);
                    list.addAll(pareMenuList);
                }
             }
        }else{//说明该组拥有所有权限
                list = svr.getAllRolePowerList(re);
        }
        re.clear();
        list = list.stream().distinct().collect(Collectors.toList());//如果有重复的  去重
        List<RolePower> lastList = new ArrayList<RolePower>();//最后的纠正过顺序的list
        for(int i =0;i<=9;i++){//使最终的list正确排序
            for(RolePower rolePower:list){
                if((i+"00").equals(rolePower.getMenuId()) ){
                    lastList.add(rolePower);
                    for(RolePower rp :list){
                        if( (i+"00").equals(rp.getPareMenuId())){
                            lastList.add(rp);
                        }
                    }
                }
            }

        }
        re.put("list",lastList);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询角色权限列表");
        return m;
    }

    @ApiOperation( value = "检测该角色Id是否已存在",notes = "" +
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
    @GetMapping(value = "/getByRoleId")
    public MessageModel getByRoleId(String roleId,String loginUserId,
                                    String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("roleId",roleId);
        List<Role> list = svr.getByRoleId(re);
        m.setData(list.size());
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"根据角色编号查询");
        return m;
    }


    @ApiOperation( value = "插入、更新角色",notes = "" +
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
    @PostMapping(value = "/upsertRole")
    public MessageModel upsertRole(@RequestBody Role role, HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.upsertRole(role);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        if("insert".equals(role.getState())){
            logs.PostRolePowerLoges(role,ip,"新增角色");
        }else{
            logs.PostRolePowerLoges(role,ip,"修改角色");
        }
        return m;
    }

    @ApiOperation( value = "删除角色",notes = "" +
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
    @GetMapping(value = "/deleteRole")
    public MessageModel deleteRole(String roleId,String loginUserId,
                                   String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String> re = new HashMap<>();
        re.put("roleId",roleId);
        int i = svr.deleteRole(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除角色");
        return m;
    }

    @ApiOperation( value = "删除角色权限",notes = "" +
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
    @GetMapping(value = "/deleteRolePower")
    public MessageModel deleteRolePower(String roleId,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("roleId",roleId);
        int i = svr.deleteRolePower(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultCode("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除角色权限");
        return m;
    }

    @ApiOperation( value = "保存角色权限",notes = "" +
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
    @PostMapping(value = "/insertRolePower")
    public MessageModel insertRolePower(@RequestBody RolePowerLog rolePowerArray,HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i=0;
        for(RolePower rp:rolePowerArray.getRolePowerArray()){
            if(!"0".equals(rp.getPareMenuId())){
                i=svr.insertRolePower(rp);
            }
        }
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String loginUserId = rolePowerArray.getLoginUserId();
        String loginGroupRoleId = rolePowerArray.getLoginGroupRoleId();
        String loginRoleId = rolePowerArray.getLoginRoleId();
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"保存角色权限");
        return m;
    }


    @ApiOperation( value = "查询所有角色信息",notes = "" +
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
    @GetMapping(value = "/getAllRoleByAdminList")
    public MessageModel getAllRoleByAdminList(String loginUserId,
                                               String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        List<Role> list = svr.getAllRoleByAdminList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询所有角色列表");
        return m;
    }

    @GetMapping(value = "/getGroupRoleName")
    public MessageModel getGroupRoleName(String groupRoleId,String loginUserId,
                                         String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("groupRoleId",groupRoleId);
        String groupRoleName = svr.getGroupRoleName(re);
        m.setData(groupRoleName);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询角色组名称");
        return m;
    }

}
