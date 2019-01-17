package com.en.adback.service.dic;

import com.en.adback.entity.dic.Blank;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/10.
 */
public interface IBlankService {

    List<Blank> getBlankList(Map<String,Object> re);

    int getBlankListTotal();

    String getMaxBlankId();

    int upsertBlank(Blank blank);

    int deleteBlank(Map<String,String> re);
}
