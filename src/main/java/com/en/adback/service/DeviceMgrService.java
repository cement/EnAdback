package com.en.adback.service;

import com.en.adback.entity.devicemgr.DeviceGroup;

import java.util.List;
import java.util.Map;

public interface DeviceMgrService {

    // 查询设备分组
    public List<DeviceGroup> deviceGroupList(Map<String,Object> paras);

    // 保存设备分组
       public int saveDeviceGroup(DeviceGroup deviceGroup);

    // 删除设备分组
     public int delDeviceGroup(Map<String,Object> paras);
}
