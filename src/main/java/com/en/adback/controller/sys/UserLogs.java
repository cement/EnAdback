package com.en.adback.controller.sys;

import com.en.adback.entity.Logs;
import com.en.adback.entity.sys.User;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserLogs {

    @Autowired private LogsMapper mapper;

    @Async
    public void insertPostLogs(User user, String ip, String logContent){
        Logs log=new Logs();
        log.setUserId(user.getUserId());
        log.setGroupRoleId(user.getLoginGroupRoleId());
        log.setLogContent(logContent);
        log.setIp(ip);
        log.setRoleId(user.getLoginRoleId());
        mapper.insertLogs(log);
    }

    @Async
    public void insertGetLogs(String loginUserId, String loginGroupRoleId, String loginRoleId,
                              String ip, String logContent) {
        Logs log=new Logs();
        log.setUserId(loginUserId);
        log.setGroupRoleId(loginGroupRoleId);
        log.setLogContent(logContent);
        log.setIp(ip);
        log.setRoleId(loginRoleId);
        mapper.insertLogs(log);
    }
}
