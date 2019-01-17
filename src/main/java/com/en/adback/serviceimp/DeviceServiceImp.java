package com.en.adback.serviceimp;

import com.en.adback.entity.devicemgr.Device;
import com.en.adback.mapper.DeviceMapper;
import com.en.adback.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("DeviceService")
public class DeviceServiceImp implements DeviceService {


    @Autowired private DeviceMapper mapper;

    @Override
    public List<Device> getDeviceAllList(Map<String, Object> map) {
        return mapper.getDeviceAllList(map);
    }


}
