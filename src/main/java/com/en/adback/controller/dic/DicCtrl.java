package com.en.adback.controller.dic;

import com.en.adback.common.MessageModel;
import com.en.adback.entity.dic.*;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.entity.sys.Role;
import com.en.adback.entity.sys.User;
import com.en.adback.service.dic.DicService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/3.
 */
@Api(value="暗访录入操作",tags={"暗访录入webapi 接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/dic", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")

public class DicCtrl {

    @Autowired
    private DicService svr;

    //企业
    @GetMapping(value = "/getEnterpriseList")
    public Map<String,Object> getEnterpriseList(String systemId){
        Map<String,Object> re = new HashMap<>();
        re.put("systemId",systemId);
        List<Enterprise> list = svr.getEnterpriseList(re);
        re.put("list",list);
        return re;
    }

    //广告公司
    @GetMapping(value = "/getAdvertCorpList")
    public Map<String,Object> getAdvertCorpList(){
        Map<String,Object> re = new HashMap<>();
        List<AdvertCorp> list = svr.getAdvertCorpList();
        re.put("list",list);
        return re;
    }

    //广告状态
    @GetMapping(value = "/getAdvertStateList")
    public Map<String,Object> getAdvertStateList(){
        Map<String,Object> re = new HashMap<>();
        List<AdvertState> list = svr.getAdvertStateList();
        re.put("list",list);
        return re;
    }

    //品牌
    @GetMapping(value = "/getBlankList")
    public Map<String,Object> getBlankList(){
        Map<String,Object> re = new HashMap<>();
        List<Blank> list = svr.getBlankList();
        re.put("list",list);
        return re;
    }

   //设备状态
    @GetMapping(value = "/getDeviceStatusList")
    public Map<String,Object> getDeviceStatusList(){
        Map<String,Object> re = new HashMap<String,Object>();
        List<DeviceStatus> list = svr.getDeviceStatusList();
        re.put("list",list);
        return  re;
    }

    //行业
    @GetMapping(value = "/getTradeList")
    public Map<String,Object> getTradeList(){
        Map<String,Object> re = new HashMap<>();
        List<Trade> list = svr.getTradeList();
        re.put("list",list);
        return re;
    }

    @GetMapping(value = "/orgList")
    public MessageModel getOrgList(){
        MessageModel model=new MessageModel();
        List<Area> list=svr.getOrgList();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        return model;
    }

    @GetMapping(value = "/areaList")
    public MessageModel getAreaList(){
        MessageModel model=new MessageModel();
        List<Area> list=svr.getAreaList();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        return model;
    }

    @GetMapping(value = "/getUserList")
    public MessageModel getUserList(){
        MessageModel model=new MessageModel();
        List<User> list=svr.getUserList();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("list",list);
        model.setData(map);
        model.setResultCode(list.size()>0?"1":"2");
        model.setResultMsg("success");
        return model;
    }

    @GetMapping(value = "/getGroupRoleList")
    public MessageModel getGroupRoleList(){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        List<GroupRole> list = svr.getGroupRoleList();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }

    @GetMapping(value = "/getRoleList")
    public MessageModel getRoleList(String groupRoleId ){
        MessageModel m = new MessageModel();
        Map<String,Object> re = new HashMap<>();
        re.put("groupRoleId",groupRoleId);
        List<Role> list = svr.getRoleList(re);
        re.clear();
        re.put("list",list);
        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        return m;
    }
}
