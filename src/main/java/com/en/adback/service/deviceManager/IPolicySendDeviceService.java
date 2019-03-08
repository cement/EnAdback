package com.en.adback.service.deviceManager;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface IPolicySendDeviceService {

    public List<String> getHaveSendPolicyDevices();


    Future<Integer> upsertHistoryAsync(int test);

    public int upsertHistory();



    public int upsertWithOrderIdIncr(String deviceId, String orderId);



    public int upsertWithOrderIdDecr(String deviceId, String orderId);



    public int  upsertWithAdvertPolicyId(String deviceId, String advertPolicysId);
}
