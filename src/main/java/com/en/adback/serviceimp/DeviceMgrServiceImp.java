package com.en.adback.serviceimp;

import com.en.adback.entity.devicemgr.DeviceGroup;
import com.en.adback.mapper.DeviceMgrMapper;
import com.en.adback.service.DeviceMgrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceMgrServiceImp implements DeviceMgrService {
    @Autowired
    private DeviceMgrMapper mapper;

    @Override
    public List<DeviceGroup> deviceGroupList(Map<String, Object> paras) {

        return mapper.deviceGroupList(paras);
    }

    @Override
    public int saveDeviceGroup(DeviceGroup deviceGroup) {
        return mapper.saveDeviceGroup(deviceGroup);
    }

    @Override
    public int delDeviceGroup(Map<String, Object> paras) {
        return mapper.delDeviceGroup(paras);
    }
}
