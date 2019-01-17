package com.en.adback.task;

import com.en.adback.service.advertmgr.IDevicePolicyService;
import com.en.adback.service.advertmgr.IFilePutinCityHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 设备播放策略定时计算
 * 每天晚11点开始计算第4天零点要播放的广告
 *
 *  * 策略运算
 *  * 每日晚 23 点开始计算
 *  * 1.广告文件下发策略
 *  * 2.第4天凌晨开始播放的各个设备的广告
 *  */

@Component
public class DevicePolicyTask {
    @Autowired
    private IDevicePolicyService psvr;
    @Autowired
    private IFilePutinCityHostService fsvr;

    //计算
    @Scheduled(cron = "1 0 00 * * ?")
    public void calPolicy() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date newTime = calendar.getTime();
        String theDate=df.format(newTime);
           //计算每日下发文件
           fsvr.calFilePutin(theDate);
           // 计算播放策略
            psvr.calDayPolicy(theDate);



    }





}
