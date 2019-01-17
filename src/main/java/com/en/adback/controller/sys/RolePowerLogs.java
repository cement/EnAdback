package com.en.adback.controller.sys;

import com.en.adback.entity.Logs;
import com.en.adback.entity.sys.Role;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/7.
 */
@Service
public class RolePowerLogs {

    @Autowired
    private LogsMapper mapper;


    @Async
    public void PostRolePowerLoges(Role role,String ip,String logContent ){
        Logs log = new Logs();
        log.setUserId(role.getLoginUserId());
        log.setGroupRoleId(role.getLoginGroupRoleId());
        log.setRoleId(role.getLoginRoleId());
        log.setIp(ip);
        log.setLogContent(logContent);
        mapper.insertLogs(log);
    }
}
