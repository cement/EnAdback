package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.List;

//下发的策略
@Data
public class DownDayPolicy {
      private String deviceId; // 设备编号
      private String playDate ; // 播放日期
      private String downloadUrl; // 文件下载地址
      private Policy policy; // 播放策略
      private boolean existInsertAd; // 是否存在插播
      private List<Integer> insertBeginTimes; // 插播开始时间点 从小到大, 时间戳 小时换算成毫秒 1000*3600*9 + 30*60*1000


    public DownDayPolicy() {
    }

    public DownDayPolicy(String deviceId, String playDate, String downloadUrl, Policy policy, boolean existInsertAd, List<Integer> insertBeginTimes) {
        this.deviceId = deviceId;
        this.playDate = playDate;
        this.downloadUrl = downloadUrl;
        this.policy = policy;
        this.existInsertAd = existInsertAd;
        this.insertBeginTimes = insertBeginTimes;
    }


}
