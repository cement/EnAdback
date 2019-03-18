package com.en.adback.entity.advertmgr;

//广告发布类

import lombok.Data;

@Data
public class AdvertPutIn {
    private String advertId ; // 广告编号
    private String advertName; // 广告名称
    private String fileName; // 广告名称
    private String downLoadFileName; //下载文件名
    private String adCorpName;
    private String adCorpId;
    private String tradeName;
    private String blankName;
    private double duration ; // 时长
    private  String fileSize; // 文件大小
    private  String  putInKind ;// 投放类型(1.购买，2.赠送）
    private int playAlone ; // (1,轮播，2.独播)
    private String policyName ; // 策略名称
    private String detail ; // 策略详情
    private String screen ; // 屏幕模式
    private String screenPolicyId; // 屏幕策略id
    private String  screenPosition   ;      // 位置
    private String  playDays;                    //发布天数
    private String checkDay ; // 审核日期
    private int nowState; // 当前状态(1. 加入未提交审核，2.审核中，3.审核通过，4.审核不通过,5.设置策略,6.待分发， 7.已下发到设备,8.替换 9.被替换，10.自动下刊,11.手动下刊)
    private String maker;
    private String playBeginTime;// 发布开始时间
    private String playEndTime;// 发布结束时间
    private String advertPolicyId; // 策略id
    private String devices;
    private int screenType;  //区分主屏幕还是关联屏
    private String state;//当前状态 导出excel用
    private String  playAloneString; //播放形式  导出excel用
    private String screenId; // 屏幕模式id
    private String screenCutId; // 切屏模式id
    public AdvertPutIn() {
    }
}
