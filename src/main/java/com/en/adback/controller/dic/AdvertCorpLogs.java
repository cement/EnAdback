package com.en.adback.controller.dic;

import com.en.adback.entity.Logs;
import com.en.adback.entity.dic.AdvertCorp;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/8.
 */
@Service
public class AdvertCorpLogs {

    @Autowired
    private LogsMapper mapper;

    @Async
    public void postAdvertCorpLogs(AdvertCorp advertCorp,String ip,String logContent){
        Logs log = new Logs();
        log.setUserId(advertCorp.getLoginUserId());
        log.setGroupRoleId(advertCorp.getLoginGroupRoleId());
        log.setRoleId(advertCorp.getLoginRoleId());
        log.setIp(ip);
        log.setLogContent(logContent);
        mapper.insertLogs(log);
    }

}
