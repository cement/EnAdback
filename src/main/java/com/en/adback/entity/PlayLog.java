package com.en.adback.entity;

import lombok.Data;
import java.sql.Date;

@Data
public class PlayLog {
    private String advertId; // 广告id
    private String advertName; // 广告名称
    private String playTime; // 播放时间（年-月-日 时-分-秒）
    private String broadcastDate; // 播放日期（年-月-日）
    private String airTime; // 播放时间（时-分-秒）
    private Double duration;// 时长
    private String policyName; // 播放策略
    private String detail; // 策略详情
    private String screen;// 屏幕模式
    private String screenPosition; // 屏幕位置
    private String formerAdvertising; // 前广告
    private String afterTheAdvertising; // 后广告
}
