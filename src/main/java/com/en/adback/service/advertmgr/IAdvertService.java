package com.en.adback.service.advertmgr;

import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertFiles;
import com.en.adback.entity.advertmgr.AdvertMedia;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import java.util.List;
import java.util.Map;

public interface IAdvertService {
    public List<Advert> advertList(Map<String,Object> re);

    public int advertCount(Map<String,Object> re);

    public List<Advert> advertAuditList(Map<String,Object> re);

    public int advertAuditCount(Map<String,Object> re);

    /**
     * 查询该广告媒体文件
     * @return
     */
       public List<AdvertMedia> advertMediaList(Map<String,Object> paras);

    /**
     * 查询该广告资料文件
     * @return
     */
       public List<AdvertFiles> advertFilesList(Map<String,Object> paras);

    // 插入广告状态历史表
    public int insertAdvertStateHis(AdvertStateHis advertStateHis);

    //修改广告表 状态字段
    public int updateAdvertState(Map<String,Object> paras);
    /**
     * 添加广告
     */
    int insertAdvert (Advert advert);
    /**
     * 修改广告
     */
    int updateAdvert (Advert advert);


    // 插入广告媒体表
    public int insertAdvertMedia(AdvertMedia advertMedia);
    // 插入广告资料表
    public int insertAdvertFile(AdvertFiles advertFiles);


    // 删除广告
    int deleteAdvert(Map<String,Object> re);
    // 查询各状态广告
     public List<Advert> advertListByNowState(Map<String,Object> paras);


    public List<AdvertStateHis> getStateHisByAdvertId(Map<String, Object> map);
}
