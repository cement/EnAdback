package com.en.adback.service.dic;

import com.en.adback.entity.dic.AdvertCorp;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/9.
 */
public interface IAdvertCorpService {

    List<AdvertCorp> getAdvertCorpList(Map<String,Object> re);

    int getAdvertCorpTotal();

    int insertAdvertCorp(AdvertCorp advertCorp);

    int updateAdvertCorp(AdvertCorp advertCorp);

    List<AdvertCorp> findByadCorpId(Map<String,String> re);

    int deleteAdCorp(Map<String,String> re);
    //查询公司 -- 广告公司选择用
    public List<AdvertCorp> advertCorpChoosedList();
}
