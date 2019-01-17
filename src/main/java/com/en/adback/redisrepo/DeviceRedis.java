package com.en.adback.redisrepo;

import com.alibaba.fastjson.JSON;
import com.en.adback.redisrepo.entity.DeState;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;


/**
 * 设备状态: [
 *                { theDate :'yyyy-mm-dd',
 *                   screens :[
 *                               {screenId:'c01',screenName:'正屏', screencuts :[{ screenCutId :'01',playAlone : 1, playTimeBegin:'00:00',playTimeEnd:'00:00',mediaFiles:[]}]}
 *                     ]
 *                }
 *        ]
 *
 *
 */
@Service
public class DeviceRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 保存设备状态
    public void addDeviceState(DeviceCutAdvert deviceCutAdvert){
        redisTemplate.opsForValue().set(deviceCutAdvert.makeRedisKey(), deviceCutAdvert.getPlayDate());
    }

    // 读取所有设备状态(deviceId +playDate+screenId+screenCutId+advertId)
    public List<DeviceCutAdvert> getDeviceState(String deviceId){
        List<DeviceCutAdvert> deStateList = new ArrayList<>();
        Set<String> keys= redisTemplate.keys(deviceId +"*");
        for (String key : keys){
              deStateList.add(new DeviceCutAdvert(key));
        }
        return deStateList;
    }

    //删除过期信息
    public void deleteMsgByPlayDate(String playDate){
        Set<String> keys= redisTemplate.keys("*" +playDate +"*");
        redisTemplate.opsForValue().getOperations().delete(keys);
    }

    // 删除广告
    public void deleteMsgByAdvertId(String advertId){
        Set<String> keys= redisTemplate.keys("*" +advertId);
        redisTemplate.opsForValue().getOperations().delete(keys);
    }


    // 查询存在轮播设备
    public List<DeviceCutAdvert>  scrollExistDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String advertId ){
        List<DeviceCutAdvert> list = new ArrayList<DeviceCutAdvert>();
        for(String thedate : playDates.split(",")) {
            Arrays.stream(deviceIds.split(",")).forEach(did -> {
                        Set<String> keys = redisTemplate.keys(did +"_" +thedate+"_" +screenId + "_"+screenCutId + "*_1_*");
                        for (String key : keys)
                        {
                              if (!key.contains(advertId))
                              {
                                  list.add(new DeviceCutAdvert(key));
                              }
                        }

                    }
            );
        }
        return list;
    }

    // 查询所有存在独播设备
    public List<DeviceCutAdvert>  aloneExistDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String advertId ){
        List<DeviceCutAdvert> list = new ArrayList<DeviceCutAdvert>();
        for(String thedate : playDates.split(",")) {
            Arrays.stream(deviceIds.split(",")).forEach(did -> {
                        Set<String> keys = redisTemplate.keys(did +"_" +thedate+ "_" +screenId + "_"+screenCutId +"*_2_*");
                        for (String key : keys)
                        {
                            if (!key.contains(advertId))
                            {
                                list.add(new DeviceCutAdvert(key));
                            }
                        }

                    }
            );
        }
        return list;
    }


    // 查询于插播时间段重合的插播设备
    public List<DeviceCutAdvert> insertDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String playTimeBegin,String playTimeEnd,String advertId ){
        List<DeviceCutAdvert> list = new ArrayList<DeviceCutAdvert>();
        for(String thedate : playDates.split(",")) {
            Arrays.stream(deviceIds.split(",")).forEach(did -> {
                    //    Set<String> keys = redisTemplate.keys(did +"_" +thedate+ "_" +screenId + "_"+screenCutId+"*");
                        Set<String> keys = redisTemplate.keys(did +"_" +thedate+ "_*");
                        for (String key : keys)
                        {
                            if (!key.contains(advertId))
                            {
                                DeviceCutAdvert dca =new DeviceCutAdvert(key);
                                if ((dca.getPlayTimeBegin().compareTo(playTimeBegin)>=0 && dca.getPlayTimeBegin().compareTo(playTimeEnd)<0)
                                     || (dca.getPlayTimeEnd().compareTo(playTimeBegin)>0 && dca.getPlayTimeEnd().compareTo(playTimeEnd)<=0)
                                     || (playTimeBegin.compareTo(dca.getPlayTimeBegin())>=0 && playTimeBegin.compareTo(dca.getPlayTimeEnd())<0)
                                     || (playTimeEnd.compareTo(dca.getPlayTimeBegin())>0 && playTimeEnd.compareTo(dca.getPlayTimeEnd())<=0)
                                        ) {
                                    list.add(new DeviceCutAdvert(key));
                                }
                            }
                        }

                    }
            );
        }
        return list;
    }
}
