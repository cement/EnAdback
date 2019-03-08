package com.en.adback.serviceimp.deviceMansger;

import com.en.adback.mapper.deviceManager.IPolicySendDeviceMapper;
import com.en.adback.service.deviceManager.IPolicySendDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
@Slf4j
@Service
public class PolicySendDeviceServiceImpl implements IPolicySendDeviceService {

    @Autowired
    private IPolicySendDeviceMapper haveSendMapper;
    @Autowired
    @Qualifier("ThreadExecutor")
    private ExecutorService taskExecutor;

    @Override
    public List<String> getHaveSendPolicyDevices() {
        return haveSendMapper.selectHaveSendPolicyDeviceList();
    }

    @Override
    public Future<Integer> upsertHistoryAsync(int test){

        Future<Integer> submit = taskExecutor.submit(() -> {
            log.info("=== 测试 ====params:{}", test);
            int result = upsertHistory();
            return result;
        });

        return submit;
    }

    @Override
    public int upsertHistory(){

        AtomicInteger atomic = new AtomicInteger(0);
        List<Map<String, String>> orderMaps = haveSendMapper.selectNewerOrderPolicyDevices();

        //从订单主表中查询数据并插入 已分配策略设备记录表
        orderMaps.stream().forEach(map->{
           String orderId = map.get("orderId");
            String advertPolicysId = map.get("advertPolicysId");
           String devices = map.get("devices");
           Arrays.stream(devices.split(",")).forEach(deviceId->{
               int count = haveSendMapper.upsertWithOrderIdIncr(deviceId, orderId);
               atomic.getAndAdd(count);
           });
        });

        //从 广告策略表中查询数据并插入 已分配策略设备记录表
        List<Map<String, String>> advertMaps = haveSendMapper.selectNewerAdvertPolicyDevices();
        advertMaps.stream().forEach(map->{
            String orderId = map.get("orderId");
            String advertPolicysId = map.get("advertPolicysId");
            String devices = map.get("devices");

            Arrays.stream(devices.split(",")).forEach(deviceId->{
                int count = haveSendMapper.upsertWithAdvertPolicyId(deviceId, advertPolicysId);
                atomic.getAndAdd(count);
            });
        });

        return atomic.get();
    }


    @Override
    public int upsertWithOrderIdIncr(String deviceIds, String orderId){
        AtomicInteger atomic = new AtomicInteger(0);
        Arrays.stream(deviceIds.split(",")).forEach(deviceId->{
            int count = haveSendMapper.upsertWithOrderIdIncr(deviceId, orderId);
            atomic.getAndAdd(count);
        });
        return atomic.get();
    }


    @Override
    public int upsertWithOrderIdDecr(String deviceIds, String orderId){
        AtomicInteger atomic = new AtomicInteger(0);
        Arrays.stream(deviceIds.split(",")).forEach(deviceId->{
            int count = haveSendMapper.upsertWithOrderIdDecr(deviceId, orderId);
            atomic.getAndAdd(count);
        });
        return atomic.get();

    }


    @Override
    public int  upsertWithAdvertPolicyId(String deviceIds, String advertPolicysId){
        AtomicInteger atomic = new AtomicInteger(0);
        Arrays.stream(deviceIds.split(",")).forEach(deviceId->{
            int count = haveSendMapper.upsertWithAdvertPolicyId(deviceId, advertPolicysId);
            atomic.getAndAdd(count);
        });
        return atomic.get();
    }


}
