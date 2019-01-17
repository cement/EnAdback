package com.en.adback.entity.calpolicy;

import lombok.Data;

import java.util.Map;

//定向轮播
@Data
public class TargetCircle {
    private Map<String,Object> targetScreen; // 各种播放屏幕集合

    public TargetCircle() {
    }

    public TargetCircle(Map<String,Object> targetScreen) {
        this.targetScreen = targetScreen;
    }
}
