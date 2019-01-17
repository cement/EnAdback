package com.en.adback.serviceimp.advertmgr;

import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.entity.advertmgr.AdvertFiles;
import com.en.adback.entity.advertmgr.AdvertMedia;
import com.en.adback.entity.advertmgr.AdvertStateHis;
import com.en.adback.entity.dic.AdvertCorp;
import com.en.adback.mapper.advertmgr.AdvertMapper;
import com.en.adback.service.advertmgr.IAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class AdvertServiceImp implements IAdvertService {
    @Autowired
    private AdvertMapper mapper;

    @Override
    public List<Advert> advertList(Map<String,Object> re) {
        return mapper.advertList(re);
    }

    @Override
    public int advertCount(Map<String, Object> re) {
        List<Map<String,Object>> list=mapper.advertCount(re);
        return Integer.parseInt(list.get(0).get("TOTAL").toString());
    }

    @Override
    public List<Advert> advertAuditList(Map<String, Object> re) {
        return mapper.advertAuditList(re);
    }

    @Override
    public int advertAuditCount(Map<String, Object> re) {
        List<Map<String,Object>> list = mapper.advertAuditCount(re);
        return Integer.parseInt( list.get(0).get("TOTAL").toString());
    }

    //查询该广告媒体文件
    @Override
    public List<AdvertMedia> advertMediaList(Map<String, Object> paras) {
        List<AdvertMedia> list = mapper.advertMediaList(paras);
        return list;
    }
    //查询该广告资料文件
    @Override
    public List<AdvertFiles> advertFilesList(Map<String, Object> paras) {
        List<AdvertFiles> list = mapper.advertFilesList(paras);
        return list;
    }


    // 插入广告状态历史表
    @Override
    public int insertAdvertStateHis(AdvertStateHis advertStateHis) {
        return mapper.insertAdvertStateHis(advertStateHis);
    }
    //修改广告表 状态字段
    @Override
    public int updateAdvertState(Map<String, Object> paras) {
        return mapper.updateAdvertState(paras);
    }

    /**
     * 添加广告
     */
    @Override
    public int insertAdvert(Advert advert) {
        return mapper.saveAdvert(advert);
    }

    /**
     * 修改广告
     * @param advert
     * @return
     */
    @Override

    public int updateAdvert(Advert advert) {
        return mapper.updateAdvert(advert);
    }

    // 插入广告媒体表
    @Override
    public int insertAdvertMedia(AdvertMedia advertMedia) {
        return mapper.insertAdvertMedia(advertMedia);
    }

    // 插入广告资料表
    @Override
    public int insertAdvertFile(AdvertFiles advertFiles) {
        return mapper.insertAdvertFile(advertFiles);
    }

    /**
     * 删除广告
     * @param re
     * @return
     */
    @Override
    public int deleteAdvert(Map<String, Object> re) {
        return  mapper.deleteAdvert(re);
    }
    // 查询各状态广告
    @Override
    public List<Advert> advertListByNowState(Map<String, Object> paras) {
        return mapper.advertListByNowState(paras);
    }

    @Override
    public List<AdvertStateHis> getStateHisByAdvertId(Map<String, Object> map) {
        return mapper.getStateHisByAdvertId(map);
    }


}
