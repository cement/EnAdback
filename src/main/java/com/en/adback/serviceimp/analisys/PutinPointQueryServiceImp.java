package com.en.adback.serviceimp.analisys;

import com.en.adback.entity.advertmgr.TableAdvertPolicys;
import com.en.adback.entity.advertmgr.TableSubAdvertPolicys;
import com.en.adback.mapper.analisys.PutinPointQueryMapper;
import com.en.adback.service.analisys.PutinPointQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PutinPointQueryServiceImp implements PutinPointQueryService {
    @Autowired
    private PutinPointQueryMapper mapper;
    List<TableAdvertPolicys> ptlist=null;
    @Override
    public List<Map<String, Object>> putinPointQueryList(String beginDate,String endDate,String deviceIds) {
        Map<String ,Object> paras = new HashMap<String,Object>(){{
            put("beginDate",beginDate);
            put("endDate",endDate);
        }};
       List<TableAdvertPolicys> plist= mapper.allAdvertPolicysList(paras);
       ptlist=new ArrayList<TableAdvertPolicys>();
       // 过滤掉非查询设备的策略
        Arrays.stream(deviceIds.split(",")).forEach(s ->{
                    List<TableAdvertPolicys> t= plist.stream().filter(p -> p.getDevices().contains(s)).collect(Collectors.toList()) ;
                    if (t.size()>0){
                        for (TableAdvertPolicys item : t){
                            ptlist.add(item);
                        }
                        plist.removeAll(t);
                    }
        });

        // 查询策略字表
        paras.put("advertPolicysIds" ,"'" + ptlist.stream().map(TableAdvertPolicys::getAdvertPolicysId).collect(Collectors.joining("','")) + "'");
        List<TableSubAdvertPolicys> psList =mapper.subAdvertPolicysList(paras);

        List<Map<String,Object>> rlist = new ArrayList<Map<String, Object>>();
        // 生成每台策略每天投放的广告数
        Arrays.stream(deviceIds.split(",")).forEach(nowdeviceid ->{
             for (TableAdvertPolicys t :   ptlist){
                 if (t.getDevices().contains(nowdeviceid)){ // 找到该设备
                     for(String nowDate : t.getPlayDates().split(",")){ // 查找当天是否有投放
                         if (nowDate.compareTo(beginDate)>=0 && nowDate.compareTo(endDate)<=0 ){

                                 psList.stream().filter(subPolicy -> subPolicy.getAdvertPolicysId().equals(t.getAdvertPolicysId())).forEach( sub ->{

                                                 rlist.add( new HashMap<String,Object>(){{
                                                        put("deviceId",nowdeviceid);
                                                        put("playAlone",t.getPlayAlone());
                                                        put("screenPolicyId",t.getScreenPolicyId());
                                                        put("screenId",t.getScreenId());
                                                        put("screenCutId",sub.getScreenCutId());
                                                        put("advertId",sub.getAdvertId());
                                                        put("theDate",nowDate);

                                                 }});



                                     }
                                 );

                         }
                     }

                 }

             }

        });
        return rlist;
    }
}
