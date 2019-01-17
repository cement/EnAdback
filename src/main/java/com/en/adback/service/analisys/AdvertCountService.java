package com.en.adback.service.analisys;

import com.en.adback.entity.AdvertCount;
import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.entity.advertmgr.AdvertPutIn;

import java.util.List;
import java.util.Map;

public interface AdvertCountService {

    //投放广告客户数量统计
    public List<AdvertCount> getAdcorpAndBlankCount(Map<String, Object> map);

    //行业投放广告数量
    public List<AdvertCount> getTradeCount(Map<String, Object> map);

    //品牌投放广告数量
    List<AdvertCount> getBlankCount(Map<String, Object> map);

    //年各策略投放广告数量
    public List<AdvertCount>  getPolicyCount(Map<String, Object> map);

    //各区县投放广告数量
    public List<AdvertCount> getQuxianCount(Map<String, Object> map);

    //广告统计查询列表
    public List<AdvertPutIn> getAdvertCountList(Map<String, Object> map);

    //广告统计查询总数
    public String getAdvertCountListTotal(Map<String, Object> map);
}
