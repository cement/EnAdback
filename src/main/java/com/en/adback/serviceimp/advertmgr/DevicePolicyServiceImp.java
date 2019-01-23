package com.en.adback.serviceimp.advertmgr;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.HttpUtil;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.entity.calpolicy.*;
import com.en.adback.entity.sys.DefaultAdvert;
import com.en.adback.entity.sys.FileHost;
import com.en.adback.mapper.advertmgr.AdvertMapper;
import com.en.adback.mapper.advertmgr.IDevicePolicyMapper;
import com.en.adback.mapper.sys.DefaultAdvertMapper;
import com.en.adback.mapper.sys.FileHostMapper;
import com.en.adback.service.advertmgr.IDevicePolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DevicePolicyServiceImp  implements IDevicePolicyService {

    @Value("${idcardurl}")
    private String idcardurl;


    @Autowired
    private IDevicePolicyMapper mapper;

    @Autowired
    private DefaultAdvertMapper defaultAdvertMapper; //取默認广告

    @Autowired
    private AdvertMapper advertMapper;

    // 获取文件服务器信息
    @Autowired
    private FileHostMapper fileHostMapper;




    Integer linknum=1;
    Integer link3num=-1;
    List<Integer> insertBeginTimes= new ArrayList<Integer>();

    String dcut01= "";
    String dcut02= "";
    String dcut03= "";
    String dcut04= "";
    String dcut05= "";
    String dcut06= "";
    String dcut07= "";
    String dcut08= "";

    //计算播放策略
    @Override
    public void calDayPolicy(String theDate) {
        // 取市州服务器
        List<FileHost> fileHostList=  fileHostMapper.getFileHostList();

        // 区默认广告
         List<DefaultAdvert> defaultAdverts=defaultAdvertMapper.getDefaultAdvertList();
         dcut01= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("01")).findFirst().get().getDefaultFileName(); //开屏提示屏 默认广告
         dcut02= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("02")).findFirst().get().getDefaultFileName(); //开屏正屏 默认广告
         dcut03= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("03")).findFirst().get().getDefaultFileName(); //定投  商标提示屏
         dcut04= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("04")).findFirst().get().getDefaultFileName(); //定投  定向投放正屏-正屏
         dcut05= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("05")).findFirst().get().getDefaultFileName(); //定投  定向投放正屏-AB-A屏
         dcut06= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("06")).findFirst().get().getDefaultFileName() ; //定投  定向投放正屏-AB-B屏
         dcut07= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("07")).findFirst().get().getDefaultFileName(); //定投  定向投放正屏-BA-B屏
         dcut08= defaultAdverts.stream().filter(o->o.getScreenCutId().equals("08")).findFirst().get().getDefaultFileName(); //定投  定向投放正屏-BA-A屏



        // 1. 取设备
        List<AllDevices> devicesList =new ArrayList<AllDevices>();

        HttpUtil httpUtil = new HttpUtil();

        try {
           String  result=  httpUtil.sendGetRequest(idcardurl+"/api/device/allUsefulDevices",null);
           Map<String,Object> remapper=JSON.parseObject(result,Map.class);
           List<Map<String,Object>> dlist= (List<Map<String,Object>>)((Map<String,Object>) remapper.get("data")).get("list");
           for(Map<String,Object> item : dlist){
               if (item.get("cityId")!=null)
               {
                   FileHost fileHost= fileHostList.stream().filter(o->o.getCityId().substring(0,4).equals(item.get("cityId").toString().substring(0,4))).collect(Collectors.toList()).get(0);
                   devicesList.add(new AllDevices(item.get("deviceId").toString(), item.get("cityId").toString(),
                                   fileHost.getIp() ,fileHost.getAppName(),fileHost.getUrl()
                           ));
               }
           }


          // devicesList=   (List<AllDevices>)     ((Map<String,Object>) remapper.get("data")).get("list");
        } catch (IOException e) {
            e.printStackTrace();
        }
         // 2.取该天策略
        Map<String,Object> paras = new HashMap<String,Object>();
        paras.put("theDate",theDate);
        List<AllDayPolicy> dayPolicyList =mapper.dayPolicyList(paras);
        List<AllDayPolicySub> dayPolicySubList=mapper.dayPolicySubList(paras);
      //  String devices= devicesList.stream().map(AllDevices::getDeviceId).collect(Collectors.joining(","));


        // 3 . 循环设备
          for (AllDevices device : devicesList){
              //初始化策略类
              DownDayPolicy downDayPolicy= new DownDayPolicy(
                      device.getDeviceId(), theDate,device.getIp()+device.getAppName()+device.getUrl(),
                      new Policy(
                              new OpenScreen(),
                              new TargetPutin(),
                              new TargetCircle(),
                              new All(),
                              new Insert()
                      ),false,new ArrayList<Integer>()
              );

              //获取开屏广告
              List<AdFile> filesup = new ArrayList<AdFile>();
              List<AdFile> filesdown = new ArrayList<AdFile>();
              List<AdFile> filesmid = new ArrayList<AdFile>();
              linknum=1;
              dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals("p001") && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                          List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId()) ).collect(Collectors.toList());
                          if (sbList.size()>1) //关联广告
                          {
                              AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                              AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                              filesup.add(new AdFile( upfile.getDownloadfilename(), -1,0,-1,-1 ) );
                              filesdown.add(new AdFile( downfile.getDownloadfilename(), -1,0,-1,-1 ) );
                          }else {
                              if (sbList.get(0).getPosition().equals("up")) {
                                  filesup.add(new AdFile(sbList.get(0).getDownloadfilename(), -1, 0, -1, -1));
                                  // 补默认广告
                                  filesdown.add(new AdFile(dcut02, -1,0,-1,-1));
                              }else {
                                  // 补默认广告
                                  filesup.add(new AdFile(dcut01, -1,0,-1,-1));
                                  filesdown.add(new AdFile(sbList.get(0).getDownloadfilename(), -1, 0, -1, -1));
                              }

                          }
                      }
              );
              downDayPolicy.getPolicy().getOpenScreen().setOpenTip((List<AdFile>)((ArrayList<AdFile>) filesup).clone());
              downDayPolicy.getPolicy().getOpenScreen().setOpenScreen((List<AdFile>)((ArrayList<AdFile>) filesdown).clone());



              // 定向投放广告

              // 定向投放-商标提示正屏
              filesup.clear();
              filesmid.clear();
              filesdown.clear();

              dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals("p002") && o.getScreenId().equals("c02") && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                              List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());

                              AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ?
                                      new AllDayPolicySub("","",dcut03,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);

                              AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ?
                                      new AllDayPolicySub("","",dcut04,0,""): sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                              filesup.add(new AdFile( upfile.getDownloadfilename(), -1,0,-1,-1 ) );

                              filesdown.add(new AdFile(downfile.getDownloadfilename(), -1,0,-1,-1 ) );
                      }
              );
              Map<String,Object> tfull = new HashMap<String,Object>(){{
                       put("targetMark", ((ArrayList<AdFile>) filesup).clone());
                       put("targetFull",((ArrayList<AdFile>) filesdown).clone());
              }};

              // 定向投放--商标提示AB屏
              filesup.clear();
              filesmid.clear();
              filesdown.clear();
              dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals("p002") && o.getScreenId().equals("c03") && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                          List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());

                          AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut03,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                          AllDayPolicySub midfile=sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut05,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).get(0);
                          AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut06,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                          filesup.add(new AdFile( upfile.getDownloadfilename(), -1,0,-1,-1 ) );
                          filesmid.add(new AdFile( midfile.getDownloadfilename(), -1,0,-1,-1 ) );
                          filesdown.add(new AdFile(downfile.getDownloadfilename(), -1,0,-1,-1 ) );
                      }
              );
              Map<String,Object> tABScreen = new HashMap<String,Object>(){{
                  put("targetMark", ((ArrayList<AdFile>) filesup).clone());
                  put("a",((ArrayList<AdFile>) filesmid).clone());
                  put("b",((ArrayList<AdFile>) filesdown).clone());
              }};
              // 定向投放--商标提示BA屏
              filesup.clear();
              filesmid.clear();
              filesdown.clear();
              dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals("p002") && o.getScreenId().equals("c04") && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                          List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());

                          AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut03,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                          AllDayPolicySub midfile=sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut07,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).get(0);
                          AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ?
                                  new AllDayPolicySub("","",dcut08,0,"") : sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                          filesup.add(new AdFile( upfile.getDownloadfilename(), -1,0,-1,-1 ) );
                          filesmid.add(new AdFile( midfile.getDownloadfilename(), -1,0,-1,-1 ) );
                          filesdown.add(new AdFile(downfile.getDownloadfilename(), -1,0,-1,-1 ) );
                      }
              );
              Map<String,Object> tBAScreen = new HashMap<String,Object>(){{
                  put("targetMark", ((ArrayList<AdFile>) filesup).clone());
                  put("b", ((ArrayList<AdFile>) filesmid).clone());
                  put("a",((ArrayList<AdFile>) filesdown).clone());
              }};

              downDayPolicy.getPolicy().getTargetPutin().setTargetScreen(new HashMap<String,Object>(){{

                        put("tfull",tfull);
                        put("tABScreen",tABScreen);
                        put("tBAScreen",tBAScreen);

              }});

               // -- 定向轮播
              downDayPolicy.getPolicy().getTargetCircle().setTargetScreen(
                   calGenaralScreen(dayPolicyList,device,dayPolicySubList,"p003","c05","c06","c07","c08",false)
              );
              // -- 全屏
              downDayPolicy.getPolicy().getAll().setTargetScreen(
                      calGenaralScreen(dayPolicyList,device,dayPolicySubList,"p004","c09","c10","c11","c12",false)
              );

              // -- 插播广告
              downDayPolicy.getPolicy().getInsert().setTargetScreen(
                      calGenaralScreen(dayPolicyList,device,dayPolicySubList,"p005","c13","c14","c15","c16",true)
              );

              if (insertBeginTimes.size()>0)
              {
                  downDayPolicy.setExistInsertAd(true);
                  downDayPolicy.setInsertBeginTimes(insertBeginTimes.stream().distinct().sorted().collect(Collectors.toList()));
              }

              // 获取所有要下载的广告文件


              // 保存该策略
              AdvertDayPolicyRole advertDayPoliceRole= new AdvertDayPolicyRole();
              advertDayPoliceRole.setDeviceId(device.getDeviceId());
              advertDayPoliceRole.setTheDay(theDate);
              advertDayPoliceRole.setRoleContent(JSON.toJSONString(downDayPolicy));
              advertDayPoliceRole.setMediaFiles(mediaDownLoadFiles(downDayPolicy));
              mapper.insertAdvertDayPoliceRole(advertDayPoliceRole);
          }

          // 设置广告状态 7.已下发到设备
        dayPolicySubList.stream().forEach(o->{
            advertMapper.updateAdvertState(new HashMap<String,Object>(){{
                put("advertId",o.getAdvertId());
                put("nowState",7);
            }});
            // 查询历史表是否该状态存在
           List<AdvertStateHis> hlist=   advertMapper.getAllStateHisByAdvertId(new HashMap<String,Object>(){{
                put("advertId",o.getAdvertId());
            }});
          if (hlist.stream().filter(oi->oi.getNowState()== 7).findFirst() == null)
          {
              advertMapper.insertAdvertStateHis( new AdvertStateHis( o.getAdvertId(), 7, "admin", "", "" ));
          }
        });
    }


    //计算 轮播，全屏，插播策略
    private Map<String,Object> calGenaralScreen(List<AllDayPolicy> dayPolicyList,AllDevices device, List<AllDayPolicySub> dayPolicySubList,
                                                String screenPolicyId,String fullscreenId,String ABscreenId,String BAscreenId,String threeScreenId,boolean isInsert){
        insertBeginTimes.clear();
        // 全屏
        List<AdFile> filesup = new ArrayList<AdFile>();
        List<AdFile> filesmid= new ArrayList<AdFile>();
        List<AdFile> filesdown= new ArrayList<AdFile>();
        dayPolicyList.stream().
                filter(o-> o.getScreenPolicyId().equals(screenPolicyId) && o.getScreenId().equals(fullscreenId) && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                    List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());
                    AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ? new AllDayPolicySub() : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                    filesup.add(new AdFile( upfile.getDownloadfilename(), upfile.getDuration()*1000,0,isInsert?  calTimeStamp(v.getPlayTimeBegin()) : -1,isInsert?  calTimeStamp(v.getPlayTimeEnd()) : -1 ) );
                    if(isInsert)  insertBeginTimes.add(calTimeStamp(v.getPlayTimeBegin()));
                }
        );
        List<AdFile> full = (List<AdFile>) ((ArrayList<AdFile>) filesup).clone();


        // AB屏
         filesup.clear();
         filesmid.clear();
         filesdown.clear();
         linknum=1;
         link3num=-1;
        dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals(screenPolicyId) && o.getScreenId().equals(ABscreenId) && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                     List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());
                     AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                     AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                     if (upfile != null && downfile != null) {
                         filesup.add(new AdFile(upfile.getDownloadfilename(), upfile.getDuration()*1000, linknum, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                         filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000, linknum, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                         linknum++;
                     }else if (upfile != null) {
                         filesup.add(new AdFile(upfile.getDownloadfilename(), upfile.getDuration()*1000, 0, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                     }else if (downfile != null){
                         filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000, 0, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                     }

                  if(isInsert && (upfile != null || downfile != null))  insertBeginTimes.add(calTimeStamp(v.getPlayTimeBegin()));

               }
        );
        Map<String,Object> ABScreen= new HashMap<String,Object>(){{
              put("a",((ArrayList<AdFile>) filesup).clone());
              put("b",((ArrayList<AdFile>) filesdown).clone());
        }};

        // BA屏
        filesup.clear();
        filesmid.clear();
        filesdown.clear();
        linknum=1;
        link3num=-1;
        dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals(screenPolicyId) && o.getScreenId().equals(BAscreenId) && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                    List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());
                    AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                    AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                    if (upfile != null && downfile != null) {
                        filesup.add(new AdFile(upfile.getDownloadfilename(), upfile.getDuration()*1000, linknum, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000, linknum, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                       linknum ++;
                    }else if (upfile != null) {
                        filesup.add(new AdFile(upfile.getDownloadfilename(), upfile.getDuration()*1000, 0, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                    }else if (downfile != null){
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000, 0, isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1));
                    }
                     if(isInsert && (upfile != null || downfile != null))  insertBeginTimes.add(calTimeStamp(v.getPlayTimeBegin()));
                }
        );
        Map<String,Object> BAScreen= new HashMap<String,Object>(){{
            put("b",((ArrayList<AdFile>) filesup).clone());
            put("a",((ArrayList<AdFile>) filesdown).clone());
        }};

        // 3屏
        filesup.clear();
        filesmid.clear();
        filesdown.clear();
        linknum=1;
        link3num=-1;
        dayPolicyList.stream().filter(o-> o.getScreenPolicyId().equals(screenPolicyId) && o.getScreenId().equals(threeScreenId) && o.getDevices().contains(device.getDeviceId())).forEach( v->{
                    List<AllDayPolicySub> sbList= dayPolicySubList.stream().filter(adps -> adps.getAdvertPolicysId().equals(v.getAdvertPolicysId())  ).collect(Collectors.toList());

                    AllDayPolicySub upfile=sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("up")).collect(Collectors.toList()).get(0);
                    AllDayPolicySub midfile=sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("mid")).collect(Collectors.toList()).get(0);
                    AllDayPolicySub downfile=sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).size()==0 ? null : sbList.stream().filter(dps -> dps.getPosition().equals("down")).collect(Collectors.toList()).get(0);
                    if (upfile != null && midfile != null && downfile != null) // 三屏关联
                    {
                        filesup.add(new AdFile( upfile.getDownloadfilename(), upfile.getDuration()*1000,link3num,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1 ) );
                        filesmid.add(new AdFile( midfile.getDownloadfilename(), midfile.getDuration()*1000,link3num,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000,link3num,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        link3num --;
                    }else if ( upfile != null && midfile != null) { // 上中關聯
                        filesup.add(new AdFile( upfile.getDownloadfilename(), upfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1 ) );
                        filesmid.add(new AdFile( midfile.getDownloadfilename(), midfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        linknum ++;
                    }else if ( upfile != null && downfile != null) { // 上下關聯
                        filesup.add(new AdFile( upfile.getDownloadfilename(), upfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1 ) );
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        linknum ++;
                    }else if ( midfile != null && downfile != null) { // 中下關聯
                        filesmid.add(new AdFile( midfile.getDownloadfilename(), midfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000,linknum,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                        linknum ++;
                    }else if (upfile != null){
                        filesup.add(new AdFile( upfile.getDownloadfilename(), upfile.getDuration()*1000,0,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1 ) );
                    }else if (midfile != null){
                        filesmid.add(new AdFile( midfile.getDownloadfilename(), midfile.getDuration()*1000,0,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                    }else if (downfile != null){
                        filesdown.add(new AdFile(downfile.getDownloadfilename(), downfile.getDuration()*1000,0,isInsert ? calTimeStamp(v.getPlayTimeBegin()) : -1, isInsert ? calTimeStamp(v.getPlayTimeEnd()) : -1  ) );
                    }

                   if(isInsert && (upfile != null ||midfile!=null || downfile != null))  insertBeginTimes.add(calTimeStamp(v.getPlayTimeBegin()));
                }
        );

        Map<String,Object> threeScreen= new HashMap<String,Object>(){{
            put("up",((ArrayList<AdFile>) filesup).clone());
            put("mid",((ArrayList<AdFile>) filesmid).clone());
            put("down",((ArrayList<AdFile>) filesdown).clone());
        }};

        return new HashMap<String,Object>(){{

                   put("full",full);
                   put("ABScreen",ABScreen);
                   put("BAScreen",BAScreen);
                   put("threeScreen",threeScreen);

        }};

    }






    // 计算时间到毫秒(hh:mm)
    private int calTimeStamp(String theTime)
    {
          int h = Integer.parseInt( theTime.substring(0,2));
          int m = Integer.parseInt( theTime.substring(3,5));
          return h*1000*3600 + m * 60*1000;
    }




    // 获取策略需下载的所有文件
    private String mediaDownLoadFiles( DownDayPolicy downDayPolicy){

        List<String> mediaFiles=new ArrayList<String>(); // 该设备应下载的文件
        // 1.开屏
        Stream<AdFile> tmp =   downDayPolicy.getPolicy().getOpenScreen().getOpenTip().stream();
        tmp=Stream.concat(tmp,downDayPolicy.getPolicy().getOpenScreen().getOpenScreen().stream());

        // 定向投放
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tfull")).get("targetMark")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tfull")).get("targetFull")).stream());

        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tABScreen")).get("targetMark")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tABScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tABScreen")).get("b")).stream());

        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tBAScreen")).get("targetMark")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tBAScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetPutin().getTargetScreen().get("tBAScreen")).get("a")).stream());


        // 定向轮播，全屏，插播
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                 downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("full")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("ABScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("ABScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("BAScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("BAScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("threeScreen")).get("up")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("threeScreen")).get("mid")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getTargetCircle().getTargetScreen().get("threeScreen")).get("down")).stream());


        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                downDayPolicy.getPolicy().getAll().getTargetScreen().get("full")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("ABScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("ABScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("BAScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("BAScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("threeScreen")).get("up")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("threeScreen")).get("mid")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getAll().getTargetScreen().get("threeScreen")).get("down")).stream());


        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                downDayPolicy.getPolicy().getInsert().getTargetScreen().get("full")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("ABScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("ABScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("BAScreen")).get("b")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("BAScreen")).get("a")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("threeScreen")).get("up")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("threeScreen")).get("mid")).stream());
        tmp=Stream.concat(tmp, ((ArrayList<AdFile>)
                ((Map<String,Object>)   downDayPolicy.getPolicy().getInsert().getTargetScreen().get("threeScreen")).get("down")).stream());


        tmp.map(AdFile::getName).distinct().forEach(o->{
            mediaFiles.add(o);
        });

        return mediaFiles.stream().collect(Collectors.joining(","));
    }


}
