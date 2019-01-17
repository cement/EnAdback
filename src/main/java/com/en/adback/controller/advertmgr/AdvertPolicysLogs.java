package com.en.adback.controller.advertmgr;

import com.en.adback.entity.Logs;
import com.en.adback.entity.advertmgr.AdvertPolicys;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/5.
 */
@Service
public class AdvertPolicysLogs {

    @Autowired
    private LogsMapper mapper;


    //保存策略日志
    @Async
    public void getAdvertPolicysLogs(AdvertPolicys advertPolicys, String ip, String logContent){
        Logs log=new Logs();
        log.setUserId(advertPolicys.getLoginUserId());
        log.setGroupRoleId(advertPolicys.getLoginGroupRoleId());
        log.setLogContent(logContent);
        log.setIp(ip);
        log.setRoleId(advertPolicys.getLoginRoleId());
        mapper.insertLogs(log);
    }
}
