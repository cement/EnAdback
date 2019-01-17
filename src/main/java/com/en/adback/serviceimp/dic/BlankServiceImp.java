package com.en.adback.serviceimp.dic;

import com.en.adback.entity.dic.Blank;
import com.en.adback.mapper.dic.BlankMapper;
import com.en.adback.service.dic.IBlankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/12/10.
 */
@Service
public class BlankServiceImp implements IBlankService{

    @Autowired
    private BlankMapper mapper;

    @Override
    public List<Blank> getBlankList(Map<String, Object> re) {
        return mapper.getBlankList(re);
    }

    @Override
    public int getBlankListTotal() {
        return mapper.getBlankListTotal();
    }

    @Override
    public String getMaxBlankId() {
        return mapper.getMaxBlankId();
    }

    @Override
    public int upsertBlank(Blank blank) {
        return mapper.upsertBlank(blank);
    }

    @Override
    public int deleteBlank(Map<String, String> re) {
        return mapper.deleteBlank(re);
    }
}
