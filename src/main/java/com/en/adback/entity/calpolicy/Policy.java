package com.en.adback.entity.calpolicy;

import lombok.Data;

//设备播放策略
@Data
public class Policy {
    private  OpenScreen openScreen; //弹屏
    private  TargetPutin targetPutin; // 定向投放
    private  TargetCircle targetCircle;// 定向轮播
    private  All all; //全屏
    private Insert insert;// 插播

    public Policy() {
    }

    public Policy(OpenScreen openScreen, TargetPutin targetPutin, TargetCircle targetCircle, All all, Insert insert) {
        this.openScreen = openScreen;
        this.targetPutin = targetPutin;
        this.targetCircle = targetCircle;
        this.all = all;
        this.insert = insert;
    }


}
