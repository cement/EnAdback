package com.en.adback.controller.dic;

import com.en.adback.entity.Logs;
import com.en.adback.entity.dic.Blank;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/8.
 */
@Service
public class BlankLogs {

    @Autowired
    private LogsMapper mapper;

    @Async
    public void postBlankLogs(Blank blank,String ip,String logContent){
        Logs log = new Logs();
        log.setUserId(blank.getLoginUserId());
        log.setGroupRoleId(blank.getLoginGroupRoleId());
        log.setLogContent(logContent);
        log.setIp(ip);
        log.setRoleId(blank.getLoginRoleId());
        mapper.insertLogs(log);
    }
}
