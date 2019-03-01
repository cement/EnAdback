package com.en.adback.entity;

import lombok.Data;

/**
 * 人类
 * 人脸识别
 */
@Data
public class AdvertAudience {
    private String adFileName; // 对应的广告文件名称
    private String sex; // 受众人姓名
    private int age;   //年龄
    private String nationId; // 籍贯编码
    private String putinTime; // 投放时间
    private String deviceId; // 设备id
    private String idcard; // 受众人身份证号
    private String nationName; // 籍贯名称
}
