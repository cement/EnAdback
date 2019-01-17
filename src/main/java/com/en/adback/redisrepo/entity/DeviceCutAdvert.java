package com.en.adback.redisrepo.entity;

import lombok.Data;

// 广告在设备屏幕上的最小信息单位
@Data
public class DeviceCutAdvert {
    private String  deviceId;
    private String  playDate;
    private String  screenId;
    private String  screenCutId;
    private String playAlone;
    private String playTimeBegin;
    private String playTimeEnd;
    private String advertId;

    public DeviceCutAdvert(String deviceId, String playDate, String screenId, String screenCutId,  String playAlone,
             String playTimeBegin, String playTimeEnd,String advertId) {
        this.deviceId = deviceId;
        this.playDate = playDate;
        this.screenId = screenId;
        this.screenCutId = screenCutId;
        this.playAlone=playAlone;
        this.playTimeBegin=playTimeBegin;
        this.playTimeEnd=playTimeEnd;
        this.advertId = advertId;
    }

    public DeviceCutAdvert() {
    }

    public DeviceCutAdvert(String deviceCutAdvertStr){
            this.deviceId=deviceCutAdvertStr.split("_")[0];
            this.playDate=deviceCutAdvertStr.split("_")[1];
            this.screenId=deviceCutAdvertStr.split("_")[2];
            this.screenCutId=deviceCutAdvertStr.split("_")[3];
            this.playAlone=deviceCutAdvertStr.split("_")[4];
            this.playTimeBegin=deviceCutAdvertStr.split("_")[5];
            this.playTimeEnd=deviceCutAdvertStr.split("_")[6];
            this.advertId=deviceCutAdvertStr.split("_")[7];
    }

    public String makeRedisKey(){
        return this.deviceId +"_" + this.playDate + "_" + this.screenId + "_" + this.screenCutId
                + "_" + this.playAlone +"_" + this.playTimeBegin + "_" + this.playTimeEnd +"_"+ this.advertId;
    }



}
