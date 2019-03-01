package com.en.adback.serviceimp.advertmgr;

import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.entity.advertmgr.AdvertPutIn;
import com.en.adback.mapper.advertmgr.AdvertPutinMapper;
import com.en.adback.service.advertmgr.IAdvertPutinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdvertPutinServiceImp implements IAdvertPutinService {
    @Autowired
    private AdvertPutinMapper mapper;
    //广告发布查询
    @Override
    public List<AdvertPutIn> advertPutInList(Map<String, Object> paras) {
        List<AdvertPutIn> list = mapper.advertPutInList(paras);
        return list;
    }

    @Override
    public List<Map<String, Object>> advertPutInListTotal(Map<String, Object> paras) {
        List<Map<String, Object>> list =mapper.advertPutInListTotal(paras);
        return list;
    }

    @Override
    public int advertSendDown(Map<String, Object> map) {
        return mapper.advertSendDown(map);
    }

    @Override
    public int addAdvertHis(Map<String, Object> map) {
        return mapper.addAdvertHis(map);
    }

    @Override
    public List<AdvertPutIn> getAdvertPutInExcel(Map<String, Object> map) {
        return mapper.getAdvertPutInExcel(map);
    }


}
