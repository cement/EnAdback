package com.en.adback.controller.sys;

import com.en.adback.entity.Logs;
import com.en.adback.entity.sys.GroupRole;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/7.
 */
@Service
public class GroupRoleLogs {

    @Autowired
    private LogsMapper mapper;

    @Async
    public void postGroupRoleLogs(GroupRole groupRole,String ip,String logContent){
        Logs log = new Logs();
        log.setUserId(groupRole.getLoginUserId());
        log.setGroupRoleId(groupRole.getLoginGroupRoleId());
        log.setRoleId(groupRole.getLoginRoleId());
        log.setIp(ip);
        log.setLogContent(logContent);
        mapper.insertLogs(log);
    }
}
