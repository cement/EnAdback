package com.en.adback.controller.sys;

import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.MD5Util;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.User;
import com.en.adback.service.sys.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/user", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class UserCtrl {
    @Autowired
    private IUserService svr;
    @Autowired
    private UserLogs ul;

    // 登录验证
    @PostMapping(value = "/validateLogin")
    public MessageModel validateLogin(@RequestBody User user, HttpServletRequest request){
        String a = MD5Util.md5Password(user.getPassWord());
        user.setPassWord(MD5Util.md5Password(user.getPassWord()));
        List<User> list= svr.validateLogin(user);
        Map<String,Object> result = new HashMap<String,Object>();
        MessageModel model=new MessageModel();
        result.put("list",list);
        model.setData(result);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ul.insertPostLogs(user,ip,"用户登录");
        return model;
    }

    //修改密码
    @GetMapping(value = "/changePassWord")
    public MessageModel changePassWord(String userId,String passWord,String newPassWord,String loginUserId,
                                       String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        passWord = MD5Util.md5Password(passWord);
        newPassWord = MD5Util.md5Password(newPassWord);
        re.put("userId",userId);
        re.put("passWord",passWord);
        re.put("newPassWord",newPassWord);
        String msg = "";
        if(svr.getUserList(re).size()>0){
            int i = svr.changePassWord(re);
            if(i>0){
                msg = "修改成功";
            }else{
                msg = "修改失败";
            }
        }else{
            msg="原密码错误";
        }
        m.setData(msg);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"修改密码");
        return m;
    }

    //根据条件查询所有用户
    @GetMapping(value = "/findAllUser")
    public MessageModel findAllUser(String loginAdmin, String userId, String userName, String tel, String groupRoleId, String roleId,int pageNo,int pageSize,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("userId",userId);
        re.put("userName",userName);
        re.put("tel",tel);
        re.put("groupRoleId",groupRoleId);
        re.put("roleId",roleId);
        re.put("pageBegin",(pageNo-1)*pageSize);
        re.put("pageSize",pageSize);
        List<User> list = new ArrayList<User>();
        if("admin".equals(loginAdmin)){//当前操作用户为admin
            list = svr.findAllUser(re);
        }else{//当前操作用户为组管理员
            list = svr.getUserByGroupRoleId(re);
        }
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"按条件查询用户");
        return m;
    }


    @GetMapping(value = "/getGroupRoleTotal")
    public MessageModel getGroupRoleTotal(String loginAdmin,String userId, String userName, String tel, String groupRoleId, String roleId){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("userId",userId);
        re.put("userName",userName);
        re.put("tel",tel);
        re.put("groupRoleId",groupRoleId);
        re.put("roleId",roleId);
        int total =0;
        if("admin".equals(loginAdmin)){
            total = svr.getGroupRoleTotal(re);
        }else{
            total = svr.getUserByGroupRoleIdTotal(re);
        }

        m.setData(total);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }



    @GetMapping(value = "/getByUserId")
    public MessageModel getByUserId(String userId,String loginUserId,
                                    String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("userId",userId);
        List<User> list = svr.getByUserId(re);
        m.setData(list.size());
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"根据用户编号查询");
        return m;
    }

    @PostMapping(value = "/insertUser")
    public MessageModel insertUser(@RequestBody User user, HttpServletRequest request){
        MessageModel m = new MessageModel();
        String passWord = user.getPassWord();
        user.setPassWord(MD5Util.md5Password(passWord));
        int i = svr.insertUser(user);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ul.insertPostLogs(user,ip,"新增用户");
        return m;
    }

    @PostMapping(value = "/updateUser")
    public MessageModel updateUser(@RequestBody User user, HttpServletRequest request){
        MessageModel m = new MessageModel();
        int i = svr.updateUser(user);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ul.insertPostLogs(user,ip,"修改用户");
        return m;
    }


    //删除用户
    @GetMapping(value = "/deleteUser")
    public MessageModel deleteUser(String userId,String loginUserId,
                                   String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String > re = new HashMap<>();
        re.put("userId",userId);
        int i = svr.deleteUser(re);
        m.setData(i);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"删除用户");
        return m;
    }

    //查询未指定组管理员的角色组
    @GetMapping(value = "/getGroupRoleForInsert")
    public MessageModel getGroupRoleForInsert(String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        List<GroupRole> list = svr.getGroupRoleForInsert();
        m.setData(list);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询未指定组管理员的角色组");
        return m;
    }

    //导出用户信息Excel
    @GetMapping(value = "/getUserExcel")
    public MessageModel getUserExcel(String loginAdmin, String userId, String userName, String tel, String groupRoleId, String roleId,int pageNo,int pageSize,String loginUserId,
                                     String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("userId",userId);
        re.put("userName",userName);
        re.put("tel",tel);
        re.put("groupRoleId",groupRoleId);
        re.put("roleId",roleId);
        List<User> list = new ArrayList<User>();
        UserMakeExcelCtrl umsc = new UserMakeExcelCtrl();
        if("admin".equals(loginAdmin)){//当前操作用户为admin
            list = svr.getUserExcelByAdmin(re);

            try {
                umsc.writeAllUserExcel(list);
                m.setData(1);
            } catch (IOException e) {
                m.setData(2);
                e.printStackTrace();
            }
        }else{//当前操作用户为组管理员
            list = svr.getUserExcelByGroupRoleId(re);
            try {
                umsc.writeGroupUserExcel(list);
                m.setData(1);
            } catch (IOException e) {
                m.setData(2);
                e.printStackTrace();
            }
        }
        re.clear();
        m.setResultCode("1");
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"按条件查询用户导出excel");
        return m;
    }


    @GetMapping(value = "/bindWx")
    public MessageModel bindWx(String userId,String wxOpenId,String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request){
        MessageModel m = new MessageModel();
        Map<String,String> re  = new HashMap<>();
        re.put("userId",userId);
        re.put("wxOpenId",wxOpenId);
        int i = svr.bindWx(re);
        m.setData(i);
        String ip=Common.getIpAddr(request);
        ul.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"公众号用户绑定微信");
        return m;
    }


    @PostMapping(value = "/appUserLogin")
    public MessageModel appUserLogin(@RequestBody User user, HttpServletRequest request){
        user.setPassWord(MD5Util.md5Password(user.getPassWord()));
        List<User> list= svr.appUserLogin(user);
        Map<String,Object> result = new HashMap<String,Object>();
        MessageModel model=new MessageModel();
        result.put("list",list);
        model.setData(result);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        String ip= Common.getIpAddr(request);
        ul.insertPostLogs(user,ip,"用户登录");
        return model;
    }



}
