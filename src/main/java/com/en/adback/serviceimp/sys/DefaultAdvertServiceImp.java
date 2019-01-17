package com.en.adback.serviceimp.sys;

import com.en.adback.entity.sys.DefaultAdvert;
import com.en.adback.mapper.sys.DefaultAdvertMapper;
import com.en.adback.service.sys.IDefaultAdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/8.
 */
@Service
public class DefaultAdvertServiceImp implements IDefaultAdvertService {

    @Autowired
    private DefaultAdvertMapper mapper ;

    @Override
    public List<DefaultAdvert> getDefaultAdvertList() {
        return mapper.getDefaultAdvertList();
    }

    @Override
    public int insertDefaultAdvert(DefaultAdvert defaultAdvert) {
        return mapper.insertDefaultAdvert(defaultAdvert);
    }


}
