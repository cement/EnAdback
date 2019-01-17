package com.en.adback.serviceimp.advertmgr;

import com.alibaba.fastjson.JSON;
import com.en.adback.common.HttpUtil;
import com.en.adback.entity.calpolicy.All;
import com.en.adback.entity.calpolicy.AllDayPolicySub;
import com.en.adback.entity.calpolicy.AllDevices;
import com.en.adback.entity.devicemgr.AllDevice;
import com.en.adback.entity.sys.DefaultAdvert;
import com.en.adback.entity.sys.FileHost;
import com.en.adback.mapper.advertmgr.IDevicePolicyMapper;
import com.en.adback.mapper.sys.FileHostMapper;
import com.en.adback.service.advertmgr.IFilePutinCityHostService;
import com.en.adback.serviceimp.sys.DefaultAdvertServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

// 计算文件下发服务器策略
@Service
public class FilePutinCityHostServiceImp implements IFilePutinCityHostService {
    @Value("${idcardurl}")
    private String idcardurl;
    @Autowired
    private IDevicePolicyMapper mapper;
    @Autowired
    private DefaultAdvertServiceImp dsvr;

    // 获取文件服务器信息
    @Autowired
    private FileHostMapper fileHostMapper;

    @Override
    public void calFilePutin(String theDate) {
        // 取市州服务器
        List<FileHost> fileHostList=  fileHostMapper.getFileHostList();


        Map<String,Object> paras = new HashMap<String,Object>();
        paras.put("theDate",theDate);
        // 1. 取设备
        List<AllDevices> devicesList =new ArrayList<AllDevices>();// mapper.usedDeviceList();
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
        } catch (IOException e) {
            e.printStackTrace();
        }


     //   String devices= devicesList.stream().map(AllDevices::getDeviceId).collect(Collectors.joining(","));
        List<AllDayPolicySub> dayPolicySubList=mapper.dayPolicySubListfiledown(paras);

        for (AllDayPolicySub sub : dayPolicySubList){
            this.insertFilehost(devicesList,sub,theDate);
        }

        // 默认广告生成
        //1. 读取市州
        String[] cityids={ "430100", "430200", "430300", "430400", "430500", "430600",
                          "430700", "430800", "430900", "431000", "431100", "431200","431300", "433100"};
        // 2. 读取默认广告名称
        List<DefaultAdvert> list= dsvr.getDefaultAdvertList();

        Arrays.stream(cityids).forEach(
                cityid -> {
                    for (DefaultAdvert advert : list){
                        Map<String,Object> pa = new HashMap<String,Object>();
                        pa.put("fileName",advert.getDefaultFileName());
                        pa.put("cityId",cityid);
                        pa.put("beginPlayTime",theDate);
                        mapper.insertFilehost(pa);
                    }

                }
        );

    }

    // 异步插入文件下载服务器表
  //  @Async
    public void  insertFilehost( List<AllDevices> devicesList , AllDayPolicySub sub,String theDate){
        List<String> cityids= devicesList.stream().filter( o -> sub.getDevices().contains( o.getDeviceId())).map(AllDevices::getCityId).distinct().collect(Collectors.toList());
        for (String cityId : cityids)
        {
            Map<String,Object> pa = new HashMap<String,Object>();
            pa.put("fileName",sub.getDownloadfilename());
            pa.put("cityId",cityId);
            pa.put("beginPlayTime",theDate);
            mapper.insertFilehost(pa);

        }
    }


   @Async
   public Future<String> asyncInsertFileHost( List<AllDevices> devicesList , AllDayPolicySub sub,String theDate){
      List<String> cityids= devicesList.stream().filter( o -> sub.getDevices().contains( o.getDeviceId())).map(AllDevices::getCityId).distinct().collect(Collectors.toList());
      for (String cityId : cityids)
      {
          Map<String,Object> pa = new HashMap<String,Object>();
          pa.put("fileName",sub.getDownloadfilename());
          pa.put("cityId",cityId);
          pa.put("beginPlayTime",theDate);
          mapper.insertFilehost(pa);

      }
      return new AsyncResult<String>(sub.getAdvertId());
  }

}
