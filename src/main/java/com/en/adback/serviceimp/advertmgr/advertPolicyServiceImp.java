package com.en.adback.serviceimp.advertmgr;

import com.en.adback.entity.advertmgr.*;
import com.en.adback.entity.calpolicy.screencut.Screen;
import com.en.adback.entity.calpolicy.screencut.ScreenCut;
import com.en.adback.mapper.advertmgr.AdvertMapper;
import com.en.adback.mapper.advertmgr.AdvertPolicyMapper;
import com.en.adback.redisrepo.DeviceRedis;
import com.en.adback.redisrepo.entity.DeScreen;
import com.en.adback.redisrepo.entity.DeScreenCut;
import com.en.adback.redisrepo.entity.DeState;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import com.en.adback.service.advertmgr.IAdvertPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class advertPolicyServiceImp implements IAdvertPolicyService {
    @Autowired
    private AdvertPolicyMapper mapper;
    @Autowired
    private AdvertMapper admapper;
    //返回所有屏幕策略集合
    @Override
    public List<PlayPolicyScreen> allPlayPolicyScreen() {
        List<PlayPolicyScreen> allPlayPolicyScreen = new ArrayList<PlayPolicyScreen>();
        allPlayPolicyScreen.add(
                new PlayPolicyScreen("p001","弹屏播放策略",new ArrayList<Screen>(Arrays.asList(
                        new Screen("c01","弹屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("01","弹屏提示屏","up","1080*66",""),
                                new ScreenCut("02","弹屏正屏","down","1080*1742","")
                        )),false)
                )),3,"eq","",false)
        );

        allPlayPolicyScreen.add(
                new PlayPolicyScreen("p002","刷屏策略",new ArrayList<Screen>(Arrays.asList(
                        new Screen("c02","刷屏-商标提示正屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("03","商标提示屏","up","1080*390",""),
                                new ScreenCut("04","刷屏正屏-正屏","down","1080*1418","")
                        )),false),
                        new Screen("c03","刷屏-商标提示AB屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("03","商标提示屏","up","1080*390",""),
                                new ScreenCut("05","刷屏正屏-AB-A屏","mid","1080*810.5",""),
                                new ScreenCut("06","刷屏正屏-AB-B屏","down","1080*607.5","")
                        )),false),
                        new Screen("c04","刷屏-商标提示BA屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("03","商标提示屏","up","1080*390",""),
                                new ScreenCut("07","刷屏正屏-BA-B屏","mid","1080*607.5",""),
                                new ScreenCut("08","刷屏正屏-BA-A屏","down","1080*810.5","")
                        )),false)

                )),7.5,"eq","",false)
        );

        allPlayPolicyScreen.add(
                new PlayPolicyScreen("p003","定向轮播策略",new ArrayList<Screen>(Arrays.asList(
                        new Screen("c05","定向轮播正屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("09","正屏","up","1080*1808","")
                        )),false),
                        new Screen("c06","定向轮播AB屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("10","AB-A屏","up","1080*1200.5",""),
                                new ScreenCut("11","AB-B屏","down","1080*607.5","")
                        )),false),
                        new Screen("c07","定向轮播BA屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("12","BA-B屏","up","1080*607.5",""),
                                new ScreenCut("13","BA-A屏","down","1080*1200.5","")
                        )),false),
                        new Screen("c08","定向轮播三屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("14","上屏","up","1080*593",""),
                                new ScreenCut("15","中屏","mid","1080*607.5",""),
                                new ScreenCut("16","下屏","down","1080*607.5","")
                        )),false)

                )),0,"","",false)
        );

        allPlayPolicyScreen.add(
                new PlayPolicyScreen("p004","全屏策略",new ArrayList<Screen>(Arrays.asList(
                        new Screen("c09","全屏正屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("09","正屏","up","1080*1808","")
                        )),false),
                        new Screen("c10","全屏AB屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("10","AB-A屏","up","1080*1200.5",""),
                                new ScreenCut("11","AB-B屏","down","1080*607.5","")
                        )),false),
                        new Screen("c11","全屏BA屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("12","BA-B屏","up","1080*607.5",""),
                                new ScreenCut("13","BA-A屏","down","1080*1200.5","")
                        )),false),
                        new Screen("c12","全屏三屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("14","上屏","up","1080*593",""),
                                new ScreenCut("15","中屏","mid","1080*607.5",""),
                                new ScreenCut("16","下屏","down","1080*607.5","")
                        )),false)

                )),0,"","",false)
        );

        allPlayPolicyScreen.add(
                new PlayPolicyScreen("p005","霸屏策略",new ArrayList<Screen>(Arrays.asList(
                        new Screen("c13","霸屏正屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("09","正屏","up","1080*1808","")
                        )),false),
                        new Screen("c14","霸屏AB屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("10","AB-A屏","up","1080*1200.5",""),
                                new ScreenCut("11","AB-B屏","down","1080*607.5","")
                        )),false),
                        new Screen("c15","霸屏BA屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("12","BA-B屏","up","1080*607.5",""),
                                new ScreenCut("13","BA-A屏","down","1080*1200.5","")
                        )),false),
                        new Screen("c16","霸屏三屏",new ArrayList<ScreenCut>(Arrays.asList(
                                new ScreenCut("14","上屏","up","1080*593",""),
                                new ScreenCut("15","中屏","mid","1080*607.5",""),
                                new ScreenCut("16","下屏","down","1080*607.5","")
                        )),false)

                )),0,"","",false)
        );

        return allPlayPolicyScreen;
    }

    // 赋值选中的屏幕策略到策略集合(从数据库读取)
    boolean c=false;
    @Override
    public AdvertPolicys readAdvertPolicys(String advertPolicysId) {
        AdvertPolicys advertPolicys= new AdvertPolicys();
        List<PlayPolicyScreen> list = allPlayPolicyScreen();
        List<TableAdvertPolicys> mainlist=mapper.advertPolicysList(new HashMap<String,Object>(){{put("advertPolicysId",advertPolicysId);}});
        TableAdvertPolicys mainp=mainlist.size()==0 ? null : mainlist.get(0);
        List<TableSubAdvertPolicys> listSub=mapper.subAdvertPolicysList(new HashMap<String,Object>(){{put("advertPolicysId",advertPolicysId);}});

        if (mainp != null){
            PlayPolicyScreen currentP=  list.stream()
                    .filter(o->o.getScreenpolicyId().equals(mainp.getScreenPolicyId()))
                    .collect(Collectors.toList()).get(0);
            currentP.setChoosed(true);
          //  currentP.setScreenpolicyId(mainp.getScreenPolicyId());
            currentP.getScreens().stream().filter(o->o.getScreenId().equals(mainp.getScreenId())).forEach(
                    v -> {
                         c=false;
                        v.getCutScreenForm().stream().forEach(vi ->{
                               for (TableSubAdvertPolicys sub : listSub)
                               {
                                      if (sub.getScreenCutId().equals(vi.getScreenCutId() ))
                                      {
                                          vi.setAdvertId(sub.getAdvertId());
                                          c=true;
                                      }
                               }
                        });
                        if (c) v.setChoosed(true);
                    }
            );
            advertPolicys.setAdvertPolicysId(advertPolicysId);
            advertPolicys.setDevices(mainp.getDevices());
            advertPolicys.setPlayAlone(mainp.getPlayAlone());
            advertPolicys.setPlayDates(mainp.getPlayDates());
            advertPolicys.setPlayTimeBegin(mainp.getPlayTimeBegin());
            advertPolicys.setPlayTimeEnd(mainp.getPlayTimeEnd());
            advertPolicys.setPutInKind(mainp.getPutInKind());
            advertPolicys.setScreenpolicys(list);
        }else {
            advertPolicys.setAdvertPolicysId(advertPolicysId);
            advertPolicys.setScreenpolicys(list);
        }

        return advertPolicys;
    }








    // 保存集合中选中的策略到数据库
    @Override
    public void insertAdvertPolicys(AdvertPolicys advertPolicys,String usedId) {
        TableAdvertPolicys mainp= new TableAdvertPolicys();
        mainp.setAdvertPolicysId(advertPolicys.getAdvertPolicysId());
        mainp.setDevices(advertPolicys.getDevices());
        mainp.setPlayAlone(advertPolicys.getPlayAlone());
        mainp.setPlayDates(advertPolicys.getPlayDates());
        mainp.setPlayTimeBegin(advertPolicys.getPlayTimeBegin());
        mainp.setPlayTimeEnd(advertPolicys.getPlayTimeEnd());
        mainp.setPutInKind(advertPolicys.getPutInKind());
        PlayPolicyScreen playPolicyScreen=advertPolicys.getScreenpolicys().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).size()==0 ? null : advertPolicys.getScreenpolicys().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).get(0);
        mainp.setScreenPolicyId(playPolicyScreen.getScreenpolicyId());
        Screen choosedScreen=null;
        if (playPolicyScreen != null)
        {
            choosedScreen= playPolicyScreen.getScreens().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).size()==0 ? null :playPolicyScreen.getScreens().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).get(0);
        }

        mainp.setScreenId(choosedScreen.getScreenId());
        // 保存主表
        mapper.insertAdvertPolicys(mainp);

        // 保存字表

        TableSubAdvertPolicys sub = new TableSubAdvertPolicys();
        Map<String,Object> paras = new HashMap<String,Object>();
        paras.put("advertPolicysId",advertPolicys.getAdvertPolicysId());
        mapper.deleteSubAdvertPolicys(paras);
        if (choosedScreen != null)
        {
            for (ScreenCut  cut :   choosedScreen.getCutScreenForm() )
            {
                 if (!cut.getAdvertId().equals(""))
                 {
                     sub= new TableSubAdvertPolicys();
                     sub.setAdvertId(cut.getAdvertId());
                     sub.setAdvertPolicysId(advertPolicys.getAdvertPolicysId());
                     sub.setScreenCutId(cut.getScreenCutId());
                     mapper.insertSubAdvertPolicys(sub);

                     // 插入广告历史表
                     AdvertStateHis advertStateHis= new AdvertStateHis();
                     advertStateHis.setAdvertId(sub.getAdvertId());
                     advertStateHis.setNowState(6);
                     advertStateHis.setMaker(usedId);
                     admapper.insertAdvertStateHis(advertStateHis);
                     //修改广告表状态
                     Map<String,Object> pas = new HashMap<String,Object>();
                     pas.put("advertId",sub.getAdvertId());
                     pas.put("nowState","6");
                     admapper.updateAdvertState(pas);
                 }
            }
        }

    }


    @Autowired
    private DeviceRedis  deviceRedis;
    Screen choosedScreen=null;
    // 保存集合中选中的策略到redis
    @Override
    public void saveAdvertPolicysRedis(AdvertPolicys advertPolicys) {
        PlayPolicyScreen playPolicyScreen=advertPolicys.getScreenpolicys().stream()
                .filter(o->o.isChoosed()).collect(Collectors.toList()).size()==0 ? null : advertPolicys.getScreenpolicys().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).get(0);

        if (playPolicyScreen != null)
        {
            choosedScreen= playPolicyScreen.getScreens().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).size()==0 ? null :playPolicyScreen.getScreens().stream().filter(o->o.isChoosed()).collect(Collectors.toList()).get(0);
        }else{
            return;
        }


        String[] playDates =advertPolicys.getPlayDates().split(",");// 播放天数
        String[] devices =advertPolicys.getDevices().split(","); // 设备数

        // 循环写入redis
                    choosedScreen.getCutScreenForm().forEach(screenCut -> {
                        if (!screenCut.getAdvertId().equals("")) {
                            // 删除redis广告
                            deviceRedis.deleteMsgByAdvertId(screenCut.getAdvertId());
                            for (String nowDeviceId : devices){
                                   for (String nowPlayDate : playDates ){

                                        deviceRedis.addDeviceState(new DeviceCutAdvert(nowDeviceId, nowPlayDate, choosedScreen.getScreenId(), screenCut.getScreenCutId(), String.valueOf( advertPolicys.getPlayAlone()),
                                                advertPolicys.getPlayTimeBegin(), advertPolicys.getPlayTimeEnd(), screenCut.getAdvertId()));
                                    }
                            }
                        }
                    });
    }

    // 查询存在轮播设备
    public List<DeviceCutAdvert>  scrollExistDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String advertId ){
        List<DeviceCutAdvert> list = deviceRedis.scrollExistDeviceIds(deviceIds, playDates,screenId,  screenCutId,advertId);
        return list;
    }

    // 查询所有存在独播设备

    /**
     * 查询所有存在独播设备
     * @param deviceIds
     * @param playDates
     * @param screenId
     * @param screenCutId
     * @param advertId
     * @return
     */
    public List<DeviceCutAdvert>  aloneExistDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String advertId){
        List<DeviceCutAdvert> list = deviceRedis.aloneExistDeviceIds(deviceIds, playDates,screenId,  screenCutId,advertId);
        return list;
    }


    // 查询于插播时间段重合的插播设备
    public List<DeviceCutAdvert> insertDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String playTimeBegin,String playTimeEnd,String advertId ){
        List<DeviceCutAdvert> list = deviceRedis.insertDeviceIds(deviceIds, playDates,screenId,  screenCutId,playTimeBegin,playTimeEnd,advertId);
        return list;
    }


    // 从redis 删除该广告
    public void delAdvertFromRedis(String advertId){
        deviceRedis.deleteMsgByAdvertId(advertId);
    }


}
