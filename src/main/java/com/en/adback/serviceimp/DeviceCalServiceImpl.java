package com.en.adback.serviceimp;

import com.en.adback.service.deviceManager.IPolicySendDeviceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Data
@Slf4j
public class DeviceCalServiceImpl  {

   private IPolicySendDeviceService deviceService;
   private  String deviceIds ;
    public DeviceCalServiceImpl(IPolicySendDeviceService deviceServic, String deviceIds) {
        this.deviceService = deviceService;
        this.deviceIds=deviceIds;
    }

    @Async
    public void insertDeviceCal() {
        //插入已分配策略设备记录表(AdvertPolicysId)
//        Arrays.stream(this.deviceIds.split(",")).forEach(deviceId->{
//            deviceService.upsertWithAdvertPolicyId(deviceId,"");
//        });

        log.info("=== 测试 === 线程：{}",Thread.currentThread().getName());
    }
}
