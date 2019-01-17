package com.en.adback.service.advertMonitor;

import com.en.adback.entity.advertmgr.AdvertDayPolicyRole;

import java.util.List;
import java.util.Map;

public interface PolicyMonitorService {

    public List<AdvertDayPolicyRole> getPolicyMonList(Map<String, Object> map);
}
