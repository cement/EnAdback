package com.en.adback.service.advertmgr;

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertPutIn;

import java.util.List;
import java.util.Map;

public interface IAdvertPutinService {

    //根据调件查询广告信息 分页
    public List<AdvertPutIn> advertPutInList(Map<String,Object> paras);

    //根据条件查询广告信息总数
    public List<Map<String,Object>> advertPutInListTotal(Map<String,Object> paras);



    //广告下发
    public int advertSendDown(Map<String, Object> map);

    //广告下发后 在广告状态历史里面添加信息
    public int addAdvertHis(Map<String, Object> map);
}
