package com.en.adback.controller;



import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.Menu;
import com.en.adback.entity.sys.RolePower;
import com.en.adback.service.IMenuService;
import io.swagger.annotations.*;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value="菜单管理",tags={"菜单管理webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/menu", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class MenuCtrl {
    @Autowired
    private IMenuService svr;
    @Autowired
    private UserLogs ulogs;


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
    @GetMapping(value="/menuList")
    public MessageModel menuList(String loginUserId,
                                 String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();
        List<Menu> list= svr.menuList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode( list.size()>0 ? "1" :"2");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询菜单");
        return m;
    }

    //查询组管理员的可控菜单
    @GetMapping(value = "/getMenuByGroupRoleId")
    public MessageModel getMenuByGroupRoleId(String actionGroupRoleId,String loginUserId,
                                             String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();
        List<Menu> listLast = new ArrayList<Menu>();
        List<Menu> list= svr.menuList();
        re.put("groupRoleId",actionGroupRoleId);
        String menus = svr.getMenus(re);//取出该小组的权限 字符串形式
        if(null != menus && !"".equals(menus)){
            if(!"0".equals(menus)){//该小组只拥有部分权限
                    String[] menuArray = menus.split(",");// 以逗号隔开 并把每个权限依次放入数组
                    String pareMenuId = "";//单个权限的开始位  如1、2、7等
                    for(int i=0;i<menuArray.length;i++){
                        String menuId = menuArray[i];//循环取出每个权限编号
                        if(!"00".equals(menuId.substring(1))){//如果是子页面 如103、 104、等
                            re.put("leafMenuId",menuId);
                            String pareMenuIdTrue = svr.getPareMenuId(re);//从数据库取出子菜单的上级菜单
                            if(!pareMenuId.equals(pareMenuIdTrue)){
                                for(Menu menu:list){
                                    if(pareMenuIdTrue.equals(menu.getMenuId())){//子菜单对应的一级菜单
                                        listLast.add(menu);
                                        break;
                                    }
                                }
                            }
                            pareMenuId = pareMenuIdTrue;
                            for(Menu menu:list){
                                if(menuId.equals(menu.getMenuId())|| menuId.equals(menu.getPareMenuId())){//筛选出组管理员拥有权限的菜单列表
                                    listLast.add(menu);
                                    break;
                                }
                            }
                        }else{ //该menuId为一级菜单 如800等
                            for(Menu menu:list){
                                if(menuId.equals(menu.getMenuId())|| menuId.equals(menu.getPareMenuId())){//筛选出组管理员拥有权限的菜单列表
                                    listLast.add(menu);
                                }
                            }
                        }
                    }
            }else{
                listLast.addAll(list);//该组管理员拥有全部权限
            }
            listLast = listLast.stream().distinct().collect(Collectors.toList());//如果有重复的  去重
        }
        re.put("list",listLast);
        m.setData(re);
        m.setResultCode( list.size()>0 ? "1" :"2");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询菜单");
        return m;
    }

    @GetMapping(value = "/getMenuByRoleId")
    public MessageModel getMenuByRoleId(String actionRoleId,String loginUserId,
                                        String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();
        re.put("roleId",actionRoleId);
        List<RolePower> list= svr.rolePowerByRole(re);
        re.put("list",list);
        m.setData(re);
        m.setResultCode( list.size()>0 ? "1" :"2");
        m.setResultMsg("ok");
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询普通用户角色权限");
        return m;
    }


}
