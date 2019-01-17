package com.en.adback.serviceimp.dic;

import com.en.adback.entity.dic.AdvertCorp;
import com.en.adback.mapper.dic.AdvertCorpMapper;
import com.en.adback.service.dic.IAdvertCorpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/9.
 */
@Service
public class AdvertCorpServiceImp implements IAdvertCorpService{

    @Autowired
    private AdvertCorpMapper mapper;

    @Override
    public List<AdvertCorp> getAdvertCorpList(Map<String, Object> re) {
        return mapper.getAdvertCorpList(re);
    }

    @Override
    public int getAdvertCorpTotal() {
        return mapper.getAdvertCorpTotal();
    }


    @Override
    public int insertAdvertCorp(AdvertCorp advertCorp) {
        return mapper.insertAdvertCorp(advertCorp);
    }

    @Override
    public int updateAdvertCorp(AdvertCorp advertCorp) {
        return mapper.updateAdvertCorp(advertCorp);
    }

    @Override
    public List<AdvertCorp> findByadCorpId(Map<String, String> re) {
        return mapper.findByadCorpId(re);
    }

    @Override
    public int deleteAdCorp(Map<String, String> re) {
        return mapper.deleteAdCorp(re);
    }

    //查询公司 -- 广告公司选择用

    @Override
    public List<AdvertCorp> advertCorpChoosedList() {
        return mapper.advertCorpChoosedList();
    }
}
