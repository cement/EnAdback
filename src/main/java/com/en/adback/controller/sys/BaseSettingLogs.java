package com.en.adback.controller.sys;

import com.en.adback.entity.Logs;
import com.en.adback.entity.sys.SysSetting;
import com.en.adback.mapper.LogsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/1/8.
 */
@Service
public class BaseSettingLogs {

    @Autowired
    private LogsMapper mapper;

    @Async
    public void postSettingParamsLogs(SysSetting sysSetting,String ip,String logContent){
        Logs log = new Logs();
        log.setUserId(sysSetting.getLoginUserId());
        log.setGroupRoleId(sysSetting.getLoginGroupRoleId());
        log.setRoleId(sysSetting.getLoginRoleId());
        log.setIp(ip);
        log.setLogContent(logContent);
        mapper.insertLogs(log);
    }
}
