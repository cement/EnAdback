package com.en.adback.entity.advertmgr;

import lombok.Data;


//android播放記錄表
@Data
public class AdvertPlayRec {
    //设备广告播放采集
//    private String deviceId; // 设备编号
//    private String fileName; // 广告文件名
//    private String paperId; //  受众人员身份证号
//    private String receiveTime; // 采集时间

    private int id;  	//id
    private String deviceId; 	//设备编号
    private String  fileName; 	//播放文件编号
    private String playTime;  	//播放时间

    public AdvertPlayRec() {
    }
}
