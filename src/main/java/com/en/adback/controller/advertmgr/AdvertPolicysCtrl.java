package com.en.adback.controller.advertmgr;


import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value="广告策略",tags={"广告策略webapi接口"})
@RestController
@CrossOrigin
@RequestMapping(value = "/api/advertPolicy", method = {RequestMethod.GET,RequestMethod.POST}, produces = "application/json;charset=UTF-8")
public class AdvertPolicysCtrl {
    @Autowired
    private IAdvertPolicyService svr;
    @Autowired
    private AdvertPolicysLogs logs;
    @Autowired
    private UserLogs ulogs;

    // 获取所有屏幕策略
    @GetMapping(value="/allScreenPolicys")
   public MessageModel allScreenPolicys(String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request) {
        MessageModel m= new MessageModel();
        Map<String,Object> re = new HashMap<String,Object>();
        List<PlayPolicyScreen> list= svr.allPlayPolicyScreen();
        re.put("list",list);

        m.setData(re);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"查询所有屏幕策略");
        return m;
    }


    // 读取策略
    @GetMapping(value="/advertPolicys")
    public MessageModel getAdvertPolicys(String advertPolicyId,String loginUserId,String loginGroupRoleId,String loginRoleId,HttpServletRequest request) {
        MessageModel m= new MessageModel();
        AdvertPolicys advertPolicys= svr.readAdvertPolicys(advertPolicyId);
        m.setData(advertPolicys);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"读取策略");
        return m;
    }









    //保存策略
    @PostMapping(value="/saveAdvertPolicys")
    public MessageModel getAdvertPolicys(@RequestBody  AdvertPolicys advertPolicys , HttpServletRequest request) {
        MessageModel m= new MessageModel();
        svr.insertAdvertPolicys(advertPolicys);
        svr.saveAdvertPolicysRedis(advertPolicys);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        logs.getAdvertPolicysLogs(advertPolicys,ip,"保存策略");
        return m;
    }


    // 判断设备该屏幕该时间段是否已存在独播
    // String deviceIds,String playDates,String screenId, String screenCutId,String advertId
    @PostMapping(value="/judgeScreenAlone")
    public MessageModel judgeScreenAlone(@RequestBody Map<String,Object> paras, HttpServletRequest request){
        MessageModel m= new MessageModel();
        List<DeviceCutAdvert> list=svr.aloneExistDeviceIds(paras.get("deviceIds").toString(),
                                                           paras.get("playDates").toString(),
                                                           paras.get("screenId").toString(),
                                                           paras.get("screenCutId").toString(),
                                                           paras.get("advertId").toString());
        m.setData(list);
        String loginUserId = paras.get("loginUserId").toString();
        String loginGroupRoleId = paras.get("loginGroupRoleId").toString();
        String loginRoleId = paras.get("loginRoleId").toString();
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"读取策略");
        return m;
    }

    // 判断设备该屏幕该时间段是否已存在轮播
    @GetMapping(value="/judgeScreenScroll")
    public MessageModel judgeScreenScroll(@RequestBody Map<String,Object> paras, HttpServletRequest request){
        MessageModel m= new MessageModel();
        List<DeviceCutAdvert> list=svr.scrollExistDeviceIds(paras.get("deviceIds").toString(),
                paras.get("playDates").toString(),
                paras.get("screenId").toString(),
                paras.get("screenCutId").toString(),
                paras.get("advertId").toString());
        m.setData(list);
        String loginUserId = paras.get("loginUserId").toString();
        String loginGroupRoleId = paras.get("loginGroupRoleId").toString();
        String loginRoleId = paras.get("loginRoleId").toString();
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"读取策略");
        return m;
    }

    // 查询于插播时间段重合的插播设备
    //String deviceIds,String playDates,String screenId, String screenCutId,String playTimeBegin,String playTimeEnd,String advertId
    @GetMapping(value="/judgeScreenInsert")
    public MessageModel judgeScreenInsert(@RequestBody Map<String,Object> paras, HttpServletRequest request){
        MessageModel m= new MessageModel();
        List<DeviceCutAdvert> list=svr.insertDeviceIds(paras.get("deviceIds").toString(),
                paras.get("playDates").toString(),
                paras.get("screenId").toString(),
                paras.get("screenCutId").toString(),
                paras.get("playTimeBegin").toString(),
                paras.get("playTimeEnd").toString(),
                paras.get("advertId").toString());
        m.setData(list);
        String loginUserId = paras.get("loginUserId").toString();
        String loginGroupRoleId = paras.get("loginGroupRoleId").toString();
        String loginRoleId = paras.get("loginRoleId").toString();
        String ip = Common.getIpAddr(request);
        ulogs.insertGetLogs(loginUserId,loginGroupRoleId,loginRoleId,ip,"读取策略");
        return m;
    }


}
