package com.en.adback.entity;

import lombok.Data;

/**
 * 广告统计查询
 */
@Data
public class AdvertCount {

    private String year;    //年份
    private String month;    //月份
    private String tradeId;    //行业id
    private String tradeName; //行业名称
    private int tradeNums;    //投放广告的行业数量
    private int adcorpNums;    //投放广告公司数量
    private String blankId;    // 品牌id
    private String blankName; //品牌名称
    private int blankNums;    //投放广告品牌数量
    private String screenPolicyId; // 屏幕播放策略id
    private String screenPolicyName; // 屏幕播放策略名称
    private int screenPolicyNums; //策略播放广告数量
    private String advertId;  // 广告id
    private String playDates;  // 播放时间
    private String devices;    // 设备id

}
