package com.en.adback.controller;

import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.Advert;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/5.
 */
@Service
public class AdvertLogs {

    @Autowired
    private LogsMapper mapper;


    @Async
    public void postAdvertLogs(Advert advert,String ip, String logContent){
        Logs log = new Logs();
        log.setUserId(advert.getLoginUserId());
        log.setGroupRoleId(advert.getLoginGroupRoleId());
        log.setRoleId(advert.getLoginRoleId());
        log.setLogContent(logContent);
        log.setIp(ip);
        mapper.insertLogs(log);
    }

}
