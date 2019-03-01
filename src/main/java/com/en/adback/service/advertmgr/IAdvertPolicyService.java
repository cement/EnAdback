package com.en.adback.service.advertmgr;

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.PlayPolicyScreen;
import com.en.adback.redisrepo.entity.DeviceCutAdvert;

import java.util.*;

public interface IAdvertPolicyService {

    //所有屏幕策略集合
    public List<PlayPolicyScreen>  allPlayPolicyScreen();

    // 赋值选中的屏幕策略到策略集合(从数据库读取)
    public AdvertPolicys readAdvertPolicys(String advertPolicysId);

    // 保存集合中选中的策略到数据库
    public void insertAdvertPolicys(AdvertPolicys advertPolicys,String usedId);


    //保存策略到redis
    public void saveAdvertPolicysRedis(AdvertPolicys advertPolicys);


    // 查询存在轮播设备
    public List<DeviceCutAdvert>  scrollExistDeviceIds(String deviceIds, String playDates, String screenId, String screenCutId, String advertId );

    // 查询所有存在独播设备
    public List<DeviceCutAdvert>  aloneExistDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String advertId );

    // 查询于插播时间段重合的插播设备
    public List<DeviceCutAdvert> insertDeviceIds(String deviceIds,String playDates,String screenId, String screenCutId,String playTimeBegin,String playTimeEnd,String advertId );

    // 从redis 删除该广告
    public void delAdvertFromRedis(String advertId);
}
