package com.en.adback.service;
import com.en.adback.entity.devicemgr.Device;

import java.util.List;
import java.util.Map;

public interface DeviceService {

    public List<Device> getDeviceAllList(Map<String, Object> map);
}
