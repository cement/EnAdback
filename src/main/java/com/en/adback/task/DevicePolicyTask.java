package com.en.adback.task;

import com.en.adback.redisrepo.DeviceRedis;
import com.en.adback.service.Adorder.IAdorderService;
import com.en.adback.service.advertmgr.IDevicePolicyService;
import com.en.adback.service.advertmgr.IFilePutinCityHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备播放策略定时计算
 * 每天晚11点开始计算第4天零点要播放的广告
 *
 *  * 策略运算
 *  * 每日晚 00 点开始计算
 *  * 1.广告文件下发策略
 *  * 2.第4天凌晨开始播放的各个设备的广告
 *  * 3. 删除前天以前过期redis
 *  * 4. 设置失效订单
 *  * 5. 删除失效订单的redis
 *  */

@Component
public class DevicePolicyTask {
    @Autowired
    private IDevicePolicyService psvr;
    @Autowired
    private IFilePutinCityHostService fsvr;
    @Autowired
     private DeviceRedis redis;
    @Autowired
    private IAdorderService order;
    //计算
    @Scheduled(cron ="1 0 0 1/1 * ?")
    public void calPolicy() {
        dealOrder();
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



    // 处理订单
    private void dealOrder(){
        // 3. 删除昨天以前过期redis
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-2);
        for (int i=2;i<=120;i++){
            Date dt=rightNow.getTime();
            String dateStr = sdf.format( dt);
            redis.deleteMsgByPlayDate(dateStr);
            rightNow.add(Calendar.DAY_OF_YEAR,-1);
        }

        //4.设置失效订单
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,-6); // 订单日期小于当前日期-6天
        Date s7= rightNow.getTime();
        String sevenDayBefore =sdf.format(s7);
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DAY_OF_YEAR,5);//最先播放日期》= 当前日期+5天
        Date s5= rightNow.getTime();
        String fiveDayBefore=sdf.format(s5);

        List<Map<String,Object>> orderList= order.willEffectOrder(new HashMap<String,Object>(){{
            put("sevenDayBefore",sevenDayBefore);
            put("fiveDayBefore",fiveDayBefore);
        }});
        //5. 数据库打上自动失效标记 ， redis 删除该订单
        orderList.stream().forEach(o ->{
            order.setOrderEffect(new HashMap<String,Object>(){{
                put("orderId",o.get("ORDERID").toString());
                put("effecter","auto");
            }});
            redis.deleteMsgByAdvertId(o.get("ORDERID").toString()+"|1");
            redis.deleteMsgByAdvertId(o.get("ORDERID").toString()+"|2");
            redis.deleteMsgByAdvertId(o.get("ORDERID").toString()+"|3");
        });
    }


}
