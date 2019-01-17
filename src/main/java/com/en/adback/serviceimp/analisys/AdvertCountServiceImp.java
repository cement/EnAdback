package com.en.adback.serviceimp.analisys;

import com.en.adback.entity.AdvertCount;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.mapper.analisys.AdvertCountMapper;
import com.en.adback.service.analisys.AdvertCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertCountServiceImp implements AdvertCountService {

    @Autowired private AdvertCountMapper mapper;

    @Override
    public List<AdvertCount> getAdcorpAndBlankCount(Map<String, Object> map) {
        return mapper.getAdcorpAndBlankCount(map);
    }

    @Override
    public List<AdvertCount> getTradeCount(Map<String, Object> map) {
        return mapper.getTradeCount(map);
    }

    @Override
    public List<AdvertCount> getBlankCount(Map<String, Object> map) {
        return mapper.getBlankCount(map);
    }

    @Override
    public List<AdvertCount> getPolicyCount(Map<String, Object> map) {
        return mapper.getPolicyCount(map);
    }

    @Override
    public List<AdvertCount> getQuxianCount(Map<String, Object> map) {
        return mapper.getQuxianCount(map);
    }

    @Override
    public List<AdvertPutIn> getAdvertCountList(Map<String, Object> map) {
        return mapper.getAdvertCountList(map);
    }

    @Override
    public String getAdvertCountListTotal(Map<String, Object> map) {
        List<Map<String,Object>> list=mapper.getAdvertCountListTotal(map);
        return list.get(0).get("TOTAL").toString();
    }
}
