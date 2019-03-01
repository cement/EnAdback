package com.en.adback.controller.advertmgr;


import com.en.adback.common.Common;
import com.en.adback.common.MessageModel;
import com.en.adback.controller.sys.UserLogs;
import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.entity.calpolicy.screencut.Screen;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import com.en.adback.service.advertmgr.IAdvertService;
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
    private IAdvertService adsvr;
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
        AdvertPolicys adPolicys=svr.readAdvertPolicys(advertPolicys.getAdvertPolicysId());
        String nowEdit="add";
        if (adPolicys.getPlayDates() !=null && !adPolicys.getPlayDates().equals("")) // 判断是否已存在，存在则为修改更新
        {
             // 删除redis 里的存储
          List<Screen> ls=  adPolicys.getScreenpolicys().stream().filter(spolicy -> spolicy.isChoosed())
                     .map(PlayPolicyScreen::getScreens).findFirst().get();
          ls.stream().filter(screen -> screen.isChoosed()).findFirst().get()
                  .getCutScreenForm().stream().forEach(
                          scut -> {
                              if( !scut.getAdvertId().equals("")){
                                  svr.delAdvertFromRedis(scut.getAdvertId());
                                  // 设置回审核状态
                                  AdvertStateHis his = new AdvertStateHis();
                                  his.setAdvertId(scut.getAdvertId());
                                  his.setMaker(advertPolicys.getLoginUserId());
                                  his.setNowState( 3);
                                  his.setMemo("修改策略改回审核状态");
                                  int i = adsvr.insertAdvertStateHis(his);
                                  Map<String,Object> map = new HashMap<String,Object>(){{
                                      put("advertId",scut.getAdvertId());
                                      put("nowState",3);
                                  }};
                                  adsvr.updateAdvertState(map);
                              }
                          }
          );
          nowEdit="edit";
        }
        svr.insertAdvertPolicys(advertPolicys,advertPolicys.getLoginUserId());
        svr.saveAdvertPolicysRedis(advertPolicys);
        m.setResultCode("1");
        m.setResultMsg("ok");
        String ip= Common.getIpAddr(request);
        if (nowEdit=="add"){
            logs.getAdvertPolicysLogs(advertPolicys,ip,"添加策略");
        }else {
            logs.getAdvertPolicysLogs(advertPolicys,ip,"修改策略(" +advertPolicys.getAdvertPolicysId() +")" );
        }

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
                                                           paras.get("advertId").toString()
                                                           );
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
