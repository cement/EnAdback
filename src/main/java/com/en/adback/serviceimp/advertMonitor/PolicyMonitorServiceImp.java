package com.en.adback.serviceimp.advertMonitor;

import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;
import com.en.adback.mapper.advertMonitor.PolicyMonitorMapper;
import com.en.adback.service.advertMonitor.PolicyMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PolicyMonitorServiceImp implements PolicyMonitorService {

    @Autowired private PolicyMonitorMapper mapper;

    @Override
    public List<AdvertDayPolicyRole> getPolicyMonList(Map<String, Object> map) {
        return mapper.getPolicyMonList(map);
    }
}
